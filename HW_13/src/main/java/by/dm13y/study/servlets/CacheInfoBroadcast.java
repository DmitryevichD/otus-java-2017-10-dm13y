package by.dm13y.study.servlets;

import by.dm13y.study.config.ContextAware;
import by.dm13y.study.services.CacheInfo;

import javax.websocket.Session;

public class CacheInfoBroadcast extends Thread {
    private final CacheInfo cacheInfo = ContextAware.context.getBean(CacheInfo.class);
    private final Session session;
    private final int delay;

    public CacheInfoBroadcast(Session session, int delay){
        this.session = session;
        this.delay = delay;
    }

    @Override
    public void run(){
        try {
            while (true) {
                if(session.isOpen()){
                    session.getBasicRemote().sendText(cacheInfo.toJson());
                }else {
                    return;
                }
                sleep(delay);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
