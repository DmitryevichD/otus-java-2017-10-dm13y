package by.dm13y.study.servlets;

import by.dm13y.study.config.ContextAware;
import by.dm13y.study.services.CacheInfo;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint(value = "/cacheinfo")
public class CacheInfoWebSocket {
    private final CacheInfo cacheInfo = ContextAware.context.getBean(CacheInfo.class);

    @OnMessage
    public void onMessage(Session session, String message){
        if (message.equals("getCacheInfo")) {
            try {
                session.getBasicRemote().sendText(cacheInfo.toJson());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
