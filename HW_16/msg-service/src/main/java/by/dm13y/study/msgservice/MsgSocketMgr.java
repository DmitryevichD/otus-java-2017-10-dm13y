package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.Header;
import by.dm13y.study.msgsys.api.MsgSocketWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class MsgSocketMgr extends Thread {
    private final int SERVICE_REG_PORT;
    private final int CHECK_INTERRUPT_TIMEOUT;
    private final MsgQueue msgQueue;
    private final static Logger logger = LoggerFactory.getLogger(MsgSocketMgr.class);
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public MsgSocketMgr(int SERVICE_REG_PORT, int CHECK_INTERRUPT_TIMEOUT, MsgQueue msgQueue) {
        this.SERVICE_REG_PORT = SERVICE_REG_PORT;
        this.CHECK_INTERRUPT_TIMEOUT = CHECK_INTERRUPT_TIMEOUT;
        this.msgQueue = msgQueue;
        setName("Socket manager");
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVICE_REG_PORT);
            serverSocket.setSoTimeout(CHECK_INTERRUPT_TIMEOUT);
        } catch (IOException e) {
            logger.error("service socket error", e);
            return;
        }
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                MsgSocketWrapper msgSocket = new MsgSocketWrapper(socket);
                Object header = msgSocket.readObjectFromSocket();
                if(header instanceof Header){
                    Header serHeader = ((Header) header);
                    msgQueue.addRecipient(serHeader, msgSocket);
                    logger.debug("Added header:" + serHeader + " to queue");

                }else {

                }

            } catch (SocketTimeoutException ex) {
                logger.info("check interrupt");
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
