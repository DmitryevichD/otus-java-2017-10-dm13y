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
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                ServerSocket serverSocket = new ServerSocket(SERVICE_REG_PORT);
                serverSocket.setSoTimeout(CHECK_INTERRUPT_TIMEOUT);
                Socket socket = serverSocket.accept();
                CompletableFuture.runAsync(() -> {
                    ObjectInputStream is = null;
                    ObjectOutputStream os = null;
                    Header header = null;

                    try {
                        is = new ObjectInputStream(socket.getInputStream());
                        os = new ObjectOutputStream(socket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        header = (Header) is.readObject();
                    } catch (Exception e) {
                        logger.error("incorrect header", e);
                    }
                    header.setId(idGenerator.incrementAndGet());
                    try {
                        os.writeObject(header);
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    msgQueue.addRecipient(header, new MsgSocketWrapper(socket));
                });
            } catch (SocketTimeoutException ex) {
                logger.info("check interrupt");
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
