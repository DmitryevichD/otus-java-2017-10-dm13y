package stabs;

import by.dm13y.study.msgsys.api.MsgRecipient;
import by.dm13y.study.msgsys.api.RecipientType;
import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgException;
import by.dm13y.study.msgsys.api.messages.MsgState;

public class MsgRecipientMock {
    public static MsgRecipient buildSimpleDBRecipient(){
        return new MsgRecipient("localhost", RecipientType.WEB_SOCKET) {
            @Override
            public void handleStateMsg(MsgState msgState) {
                System.out.println(msgState.getAvailableHeaders());
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
