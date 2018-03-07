package stabs;

import by.dm13y.study.msgservice.MsgService;
import by.dm13y.study.msgservice.MsgServiceImpl;

public class MsgServiceMock {
    public static MsgService buildSimpleMsgService(){
        return new MsgServiceImpl();
    }
}
