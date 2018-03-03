package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.MsgRecipient;

public class MsgServiceImpl implements MsgService{
    private final int port = MsgRecipient.getMsgSysPort();
    private final MsgQueue msgQueue = new MsgQueue();
    private Thread socketMgr;
    private Thread socketProcessor;
    @Override
    public void start() {
        socketMgr = new MsgSocketMgr(port, 1000, msgQueue);
        socketMgr.start();
        socketProcessor = new MsgSocketProcessor(msgQueue, 20);
        socketProcessor.start();
    }

    @Override
    public void stop(){
        socketMgr.interrupt();
        socketProcessor.interrupt();
        try {
            socketMgr.join();
            socketProcessor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
