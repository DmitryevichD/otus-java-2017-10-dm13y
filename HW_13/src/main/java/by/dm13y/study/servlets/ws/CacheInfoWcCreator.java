package by.dm13y.study.servlets.ws;

import by.dm13y.study.services.CacheInfo;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class CacheInfoWcCreator implements WebSocketCreator{
    private final CacheInfo cacheInfo;

    public CacheInfoWcCreator(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return new CacheInfoWc(cacheInfo);
    }
}
