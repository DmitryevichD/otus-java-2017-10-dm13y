package stabs;

import by.dm13y.study.msgsys.api.MsgRecipient;
import by.dm13y.study.msgsys.api.SenderType;
import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgException;
import by.dm13y.study.msgsys.api.messages.MsgSys;

public class MsgRecipientMock {
    public static MsgRecipient buildSimpleDBRecipient(){
        return new MsgRecipient("localhost", SenderType.WEB_SOCKET) {

            @Override
            public void handleSysMsg(MsgSys msgState, MsgSys.Operation operId) {
                throw new UnsupportedOperationException();

            }

            @Override
            public void handleExceptionMsg(MsgException msgException) {
                throw new UnsupportedOperationException();

            }

            @Override
            public void handleReceiveMsg(Message msg) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
