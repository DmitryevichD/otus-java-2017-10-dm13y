package stabs;

import by.dm13y.study.msgsys.core.MsgService;
import by.dm13y.study.msgsys.core.MsgServiceImpl;

public class MsgServiceMock {
    public static MsgService buildSimpleMsgService(){
        return new MsgServiceImpl();
    }
}
