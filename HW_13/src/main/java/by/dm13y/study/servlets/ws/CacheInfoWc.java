package by.dm13y.study.servlets.ws;

import by.dm13y.study.services.CacheInfo;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class CacheInfoWc {
    private final CacheInfo cacheInfo;
    private Session session;

    public CacheInfoWc(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    @OnWebSocketMessage
    public void onMessage(String data){
        if(data.equals("getCacheInfo")) {
            try {
                session.getRemote().sendString(cacheInfo.toJson());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new UnsupportedOperationException();
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
    }
}
