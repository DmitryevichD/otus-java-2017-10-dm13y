package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.Header;
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

    public MsgSocketProcessor(MsgQueue msgQueue, long processing_delay) {
        this.msgQueue = msgQueue;
        this.PROCESSING_DELAY = processing_delay;
        setName("Msg socket processor");
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            Map<Header, MsgSocketWrapper> socketMap = msgQueue.getSocketMap();
            for (Map.Entry<Header, MsgSocketWrapper> entry : socketMap.entrySet()) {
                CompletableFuture.runAsync(() -> {
                    List<Message> messages = null;
                    try {
                        messages = entry.getValue().readMessages();
                    } catch (IOException e) {
                        logger.error("Read messages from socket error", e);
                    }
                    if ((messages != null) && (!messages.isEmpty())) {
                        ConcurrentLinkedQueue<Message> input = msgQueue.getInputQueue().get(entry.getKey());
                        input.addAll(messages);
                    }

                    ConcurrentLinkedQueue<Message> output = msgQueue.getOutputQueue().get(entry.getKey());
                    for (Message message : output) {
                        entry.getValue().writeMessage(message);
                    }
                });
            }

            try {
                sleep(PROCESSING_DELAY);
            } catch (InterruptedException e) {
                logger.info("Thread is interrupted", e);
            }
        }
    }

}
