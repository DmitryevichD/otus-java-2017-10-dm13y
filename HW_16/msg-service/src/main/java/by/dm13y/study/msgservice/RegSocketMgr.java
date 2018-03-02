package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class RegSocketMgr extends Thread {
    private final int SERVICE_REG_PORT;
    private final int CHECK_INTERRUPT_TIMEOUT;
    private final MsgQueue msgQueue;
    private final static Logger logger = LoggerFactory.getLogger(RegSocketMgr.class);
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public RegSocketMgr(int SERVICE_REG_PORT, int CHECK_INTERRUPT_TIMEOUT, MsgQueue msgQueue) {
        this.SERVICE_REG_PORT = SERVICE_REG_PORT;
        this.CHECK_INTERRUPT_TIMEOUT = CHECK_INTERRUPT_TIMEOUT;
        this.msgQueue = msgQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                ServerSocket serverSocket = new ServerSocket(SERVICE_REG_PORT);
                serverSocket.setSoTimeout(CHECK_INTERRUPT_TIMEOUT);
                Socket socket = serverSocket.accept();
                CompletableFuture.runAsync(() -> {
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream(););
                    Header header = (Header) is.readObject();
                    header.setId(idGenerator.incrementAndGet());
                    try {
                        os.writeObject(header);
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    msgQueue.addRecipinet(header);
                });
            } catch (SocketTimeoutException ex) {
                logger.info("check interrupt");
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
