package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.MsgRecipient;

public class MsgServiceImpl implements MsgService{
    private final int port = MsgRecipient.getMsgSysPort();
    private final MsgQueue msgQueue = new MsgQueue();
    private Thread socketMgr;
    @Override
    public void start() {
        socketMgr = new MsgSocketMgr(port, 1000, msgQueue);
        socketMgr.start();
    }

    @Override
    public void stop(){
        socketMgr.interrupt();
    }
}
