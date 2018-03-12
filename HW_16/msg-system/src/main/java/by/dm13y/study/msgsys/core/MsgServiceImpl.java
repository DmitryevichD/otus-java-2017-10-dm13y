package by.dm13y.study.msgsys.core;

import by.dm13y.study.msgsys.api.MsgRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class MsgServiceImpl implements MsgService{
    private final int port = MsgRecipient.getMsgSysPort();
    private final MsgQueue msgQueue = new MsgQueue();
    private Thread socketMgr;
    private Thread socketProcessor;
    private Thread queueProcessor;
    private final static Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);
    private final static AtomicInteger ID_GEN = new AtomicInteger(0);

    private static Integer newSystemId(){
        return ID_GEN.decrementAndGet();
    }

    @Override
    public void start() {
        socketMgr = new MsgSocketMgr(port, 1000, msgQueue);
        socketMgr.start();
        socketProcessor = new MsgSocketProcessor(msgQueue, 20);
        socketProcessor.start();
        queueProcessor = new MsgQueueProcessor(msgQueue, 20, newSystemId());
        queueProcessor.start();
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
