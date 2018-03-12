package by.dm13y.study.msgsys.webclient.websockets;

import by.dm13y.study.msgsys.webclient.msg.MsgClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CacheInfoWS extends TextWebSocketHandler {
    private MsgClient msgClient;

    @Autowired
    public void setMsgClient(MsgClient msgClient) {
        this.msgClient = msgClient;
        this.msgClient.connect();
        this.msgClient.getDBRecipientsList();
        Thread msgClientThread = new Thread(msgClient);
        msgClientThread.start();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        msgClient.getCacheInfoFromDB(session);
    }
}
