package by.dm13y.study.msgsys.core;

import by.dm13y.study.msgsys.api.MsgRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgServiceImpl implements MsgService{
    private final int port = MsgRecipient.getMsgSysPort();
    private final MsgQueue msgQueue = new MsgQueue();
    private Thread socketMgr;
    private Thread socketProcessor;
    private Thread queueProcessor;
    private final static Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);
    @Override
    public void start() {
        socketMgr = new MsgSocketMgr(port, 1000, msgQueue);
        socketMgr.start();
        logger.info("socket msr started");
        socketProcessor = new MsgSocketProcessor(msgQueue, 20);
        socketProcessor.start();
        logger.info("socket processor started");
        queueProcessor = new MsgQueueProcessor(msgQueue, 20);
        queueProcessor.start();
        logger.info("queue processor started");
    }

    @Override
    public void stop(){
        socketMgr.interrupt();
        socketProcessor.interrupt();
        queueProcessor.interrupt();
        try {
            socketMgr.join();
            socketProcessor.join();
            queueProcessor.join();
        } catch (InterruptedException e) {
            logger.info("interrupt exception");
        }
    }

    @Override
    public boolean isStarted() {
        return ((MsgSocketMgr) socketMgr).isStarted() && (((MsgSocketProcessor) socketProcessor).isStarted());
    }
}
