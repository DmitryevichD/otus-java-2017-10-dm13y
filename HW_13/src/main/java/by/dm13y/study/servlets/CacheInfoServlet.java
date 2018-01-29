package by.dm13y.study.servlets;

import by.dm13y.study.services.CacheInfo;
import by.dm13y.study.servlets.ws.CacheInfoWcCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/cacheinfo")
public class CacheInfoServlet extends WebSocketServlet {
    private CacheInfo cacheInfo;

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.setCreator(new CacheInfoWcCreator(cacheInfo));
    }

    @Override
    public void init() throws ServletException {
        ApplicationContext ac = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        cacheInfo = ac.getBean(CacheInfo.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("miss", cacheInfo.getMis());
//        req.setAttribute("hit", cacheInfo.getHit());
//        req.setAttribute("capacity", cacheInfo.getCapacity());
//        req.setAttribute("cacheSize", cacheInfo.getCacheSize());
//        req.setAttribute("eviction", cacheInfo.getEviction());
//        req.setAttribute("idle", cacheInfo.getTimeToIdleMs());
//        req.setAttribute("life", cacheInfo.getTimeToLiveMs());
        req.getRequestDispatcher("/cacheinfo.ftl").forward(req, resp);
    }


}
