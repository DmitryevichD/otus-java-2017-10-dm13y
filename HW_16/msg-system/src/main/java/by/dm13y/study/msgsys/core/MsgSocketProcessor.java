package by.dm13y.study.msgsys.core;

import by.dm13y.study.msgsys.api.Sender;
import by.dm13y.study.msgsys.api.MsgSocketWrapper;
import by.dm13y.study.msgsys.api.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgSocketProcessor extends Thread {
    private final MsgQueue msgQueue;
    private final long PROCESSING_DELAY;
    private final static Logger logger = LoggerFactory.getLogger(MsgSocketProcessor.class);
    private volatile boolean isStarted = false;

    public MsgSocketProcessor(MsgQueue msgQueue, long processing_delay) {
        this.msgQueue = msgQueue;
        this.PROCESSING_DELAY = processing_delay;
        setName("Msg socket processor");
    }

    public boolean isStarted(){
        return isStarted;
    }

    @Override
    public void run(){
        isStarted = true;
        while(!isInterrupted()){
            Map<Sender, MsgSocketWrapper> socketMap = msgQueue.getSocketMap();
            for (Map.Entry<Sender, MsgSocketWrapper> entry : socketMap.entrySet()) {
                CompletableFuture.runAsync(() -> {
                    List<Message> messages = null;
                    MsgSocketWrapper socket = entry.getValue();
                    Sender sender = entry.getKey();

                    try {
                        messages = socket.readMessages();
                    } catch (IOException e) {
                        logger.error("Read messages from socket error", e);
                    }

                    if ((messages != null) && (!messages.isEmpty())) {
                        ConcurrentLinkedQueue<Message> input = msgQueue.getInputQueue(sender);
                        if(!input.addAll(messages)){
                            logger.error(messages.toString() + " is not added to input queue for " + sender);
                        }
                        logger.info("input queue size:" + input.size());
                    }

                    ConcurrentLinkedQueue<Message> output = msgQueue.getOutputQueue(sender);
                    while (!output.isEmpty()){
                        Message message = output.poll();
                        socket.writeMessage(message);
                    }
                });
            }

            try {
                sleep(PROCESSING_DELAY);
            } catch (InterruptedException e) {
                interrupt();
                logger.info("Thread is interrupted");
            }
        }
    }

}
