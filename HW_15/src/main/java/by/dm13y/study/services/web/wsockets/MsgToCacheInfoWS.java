package by.dm13y.study.services.web.wsockets;

import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MsgRecipient;

public abstract class MsgToCacheInfoWS extends Message {
    public MsgToCacheInfoWS(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(MsgRecipient msgRecipient) {
        if (msgRecipient instanceof WebSocketMsgr) {
            exec((WebSocketMsgr)msgRecipient);
        }
    }

    public abstract void exec(WebSocketMsgr webSocketMsgr);
}
