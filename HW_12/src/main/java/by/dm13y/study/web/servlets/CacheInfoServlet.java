package by.dm13y.study.web.servlets;

import by.dm13y.study.cache.CacheLRU;
import by.dm13y.study.web.model.CacheInfo;
import com.sun.media.jfxmediaimpl.platform.PlatformManager;
import org.hibernate.jpa.internal.ManagedFlushCheckerLegacyJpaImpl;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Map;

public class CacheInfoServlet extends HttpServlet {
    private final String ROOT_PATH;
    public CacheInfoServlet(String rootPath) {
        ROOT_PATH  = rootPath;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("miss", CacheInfo.getMis());
        req.setAttribute("hit", CacheInfo.getHit());
        req.setAttribute("capacity", CacheInfo.getCapacity());
        req.setAttribute("cacheSize", CacheInfo.getCacheSize());
        req.setAttribute("eviction", CacheInfo.getEviction());
        req.setAttribute("idle", CacheInfo.getTimeToIdleMs());
        req.setAttribute("life", CacheInfo.getTimeToLiveMs());
        req.getRequestDispatcher(ROOT_PATH + "/cashinfo.ftl").forward(req, resp);
    }
}
