package by.dm13y.study.msgservice;


import by.dm13y.study.msgsys.api.Header;
import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgException;
import by.dm13y.study.msgsys.api.messages.MsgState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueueProcessor extends Thread{
    private final int PROCESSING_DELAY;
    private final MsgQueue msgQueue;
    private final static Logger logger = LoggerFactory.getLogger(MsgQueueProcessor.class);

    public MsgQueueProcessor(MsgQueue msgQueue, int processing_delay){
        this.msgQueue = msgQueue;
        this.PROCESSING_DELAY = processing_delay;
        setName("Msg queue processor");
    }

    @Override
    public void run(){
        while (!isInterrupted()) {
            Map<Header, ConcurrentLinkedQueue<Message>> queueMap = msgQueue.getInputQueue();
            for (Map.Entry<Header, ConcurrentLinkedQueue<Message>> entry : queueMap.entrySet()) {
                if(entry.getValue().isEmpty()){
                    CompletableFuture.runAsync(() -> {
                        ConcurrentLinkedQueue<Message> queue = entry.getValue();
                        while (!queue.isEmpty()) {
                            Message msg = queue.poll();
                            if (msg instanceof MsgState) {
                                msg.setState(msgQueue.getSocketMap().keySet());
                                msgQueue.getOutputQueue().get(entry.getKey()).add(msg);
                            }else {
                                Long recipientId = msg.getToId();
                                ConcurrentLinkedQueue<Message> outQuery = msgQueue.getOutputQueue().get(recipientId);
                                if(outQuery == null){
                                    Message exception = new MsgException(msg, new UnsupportedOperationException("recipient not found"));
                                    msgQueue.getOutputQueue().get(msg.getFrom()).add(exception);
                                }else{
                                    outQuery.add(msg);
                                }
                            }
                        }
                    });
                }
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
