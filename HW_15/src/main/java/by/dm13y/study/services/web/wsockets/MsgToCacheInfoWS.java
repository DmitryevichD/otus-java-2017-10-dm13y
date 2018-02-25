package by.dm13y.study.services.web.wsockets;

import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MsgRecipient;

public class MsgToCacheInfoWS extends Message {
    private final String cacheInfo;
    public MsgToCacheInfoWS(Address from, Address to, String cacheInfo) {
        super(from, to);
        this.cacheInfo = cacheInfo;
    }

    @Override
    public void exec(MsgRecipient msgRecipient) {
        if (msgRecipient instanceof WebSocketMsgr) {
            exec((WebSocketMsgr)msgRecipient);
        }
    }

    public void exec(WebSocketMsgr webSocketMsgr){
        webSocketMsgr.sendMsgToClient(cacheInfo);
    }
}
