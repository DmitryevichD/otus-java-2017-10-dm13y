package by.dm13y.study.servlets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/cacheinfo")
public class CacheInfoWebSocket {
    private final int delay = 2000;
    private Thread thread;

    @OnOpen
    public void onOpen(Session session){
        thread = new CacheInfoBroadcast(session, delay);
        thread.start();

    }

    @OnClose
    public void onClose(Session session){
        if (thread.isInterrupted()){
            thread.interrupt();
        }
    }
}
