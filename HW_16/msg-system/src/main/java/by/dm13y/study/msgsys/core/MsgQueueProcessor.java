package by.dm13y.study.msgsys.core;


import by.dm13y.study.msgsys.api.MessageHelper;
import by.dm13y.study.msgsys.api.Sender;
import by.dm13y.study.msgsys.api.SenderType;
import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgException;
import by.dm13y.study.msgsys.api.messages.MsgSys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueueProcessor extends Thread{
    private final int PROCESSING_DELAY;
    private final MsgQueue msgQueue;
    private final static Logger logger = LoggerFactory.getLogger(MsgQueueProcessor.class);
    private Sender sender = new Sender(SenderType.MSG_CORE_SYS);

    public MsgQueueProcessor(MsgQueue msgQueue, int processing_delay){
        this.msgQueue = msgQueue;
        this.PROCESSING_DELAY = processing_delay;
        setName("Msg queue processor");
        sender.setId(-1);
    }

    @Override
    public void run(){
        while (!isInterrupted()) {
            Map<Sender, ConcurrentLinkedQueue<Message>> queueMap = msgQueue.getInputQueue();
            for (Map.Entry<Sender, ConcurrentLinkedQueue<Message>> entry : queueMap.entrySet()) {
                Sender inputQueueSender = entry.getKey();
                ConcurrentLinkedQueue<Message> senderInputQueue = entry.getValue();

                if(!senderInputQueue.isEmpty()){
                    CompletableFuture.runAsync(() -> {
                        while (!senderInputQueue.isEmpty()) {
                            Message msg = senderInputQueue.poll();
                            if (msg instanceof MsgSys) {
                                MsgSys msgSys = ((MsgSys) msg);
                                if(MsgSys.Operation.byMsg(msgSys) == MsgSys.Operation.RECIPIENT_LIST){
                                    Message senderListMsg = MessageHelper.buildResponce(sender, msg,  msgQueue.getRecipientId());
                                    msgQueue.addToOutputQueue(inputQueueSender, senderListMsg);
                                }else {
                                    logger.error("System msg operation is not supported");
                                    Message exception = new MsgException(sender, msg, new UnsupportedOperationException("System msg operation is not supported"));
                                    msgQueue.addToOutputQueue(inputQueueSender, exception);
                                }
                            }else {
                                Integer recipientId = msg.getRecipientId();
                                try {
                                    msgQueue.addToOutputQueue(recipientId, msg);
                                } catch (UnsupportedOperationException ex) {
                                    logger.error("recipient not found");
                                    Message exception = new MsgException(sender, msg, ex);
                                    msgQueue.addToOutputQueue(inputQueueSender, exception);
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
