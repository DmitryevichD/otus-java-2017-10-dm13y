package by.dm13y.study.msgsys.webclient.websockets;

import by.dm13y.study.msgsys.webclient.msg.MsgClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CacheInfoWS extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(CacheInfoWS.class);
    private final MsgClient msgClient = new MsgClient();

    @PostConstruct
    public void init(){
        msgClient.connect();
        msgClient.getDBRecipientsList();
    }

    public void checkInputMsg(){
        try {
            msgClient.getMsg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        msgClient.getCacheInfoFromDB(session);
    }
}
