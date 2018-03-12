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

class MsgQueueProcessor extends Thread{
    private final String THREAD_NAME = "Msg queue processor";
    private final int PROCESSING_DELAY;
    private final MsgQueue msgQueue;
    private final static Logger logger = LoggerFactory.getLogger(MsgQueueProcessor.class);
    private final Sender sender;

    public MsgQueueProcessor(MsgQueue msgQueue, int processing_delay, int senderId){
        this.msgQueue = msgQueue;
        this.PROCESSING_DELAY = processing_delay;
        this.sender = new Sender(SenderType.MSG_CORE_SYS);
        this.sender.setId(senderId);
        setName(THREAD_NAME);
    }

    @Override
    public void run(){
        logger.info(THREAD_NAME + " is started");
        while (!isInterrupted()) {
            Map<Sender, ConcurrentLinkedQueue<Message>> queueMap = msgQueue.getInputQueue();
            for (Map.Entry<Sender, ConcurrentLinkedQueue<Message>> entry : queueMap.entrySet()) {
                Sender inputQueueSender = entry.getKey();
                ConcurrentLinkedQueue<Message> senderInputQueue = entry.getValue();

                if(!senderInputQueue.isEmpty()){
                    logger.debug("Handle input queue for " + inputQueueSender);
                    CompletableFuture.runAsync(() -> {
                        while (!senderInputQueue.isEmpty()) {
                            Message msg = senderInputQueue.poll();
                            logger.debug("GET " + msg);
                            if (msg instanceof MsgSys) {
                                MsgSys msgSys = ((MsgSys) msg);
                                MsgSys.Operation operation = MsgSys.Operation.byMsg(msgSys);
                                logger.debug("Handle as system massage with operation: " + operation);
                                if(operation == MsgSys.Operation.DB_RECIPIENT_LIST){
                                    Message senderListMsg = MessageHelper.buildResponse(sender, msgSys,  msgQueue.getRecipientDBList(SenderType.DB_SERVICE));
                                    msgQueue.addToOutputQueue(inputQueueSender, senderListMsg);
                                }else {
                                    logger.error(operation + " is not supported");
                                    Message exception = new MsgException(sender, msg, new UnsupportedOperationException(operation + " is not supported"));
                                    msgQueue.addToOutputQueue(inputQueueSender, exception);
                                }
                            }else {
                                logger.debug("Handle as simple massage");
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
        logger.info(THREAD_NAME + " is stopped");
    }
}
