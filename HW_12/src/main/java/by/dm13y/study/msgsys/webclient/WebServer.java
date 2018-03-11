package by.dm13y.study.msgsys.webclient;

import by.dm13y.study.msgsys.webclient.model.CacheExecutor;
import by.dm13y.study.msgsys.webclient.servlets.CacheInfoServlet;
import freemarker.ext.servlet.FreemarkerServlet;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import java.util.Collections;

public class WebServer {
    private final static int SERVER_PORT = 8088;
    private final static String JETTY_ROOT = "HW_12/";


    private static ConstraintSecurityHandler buildSecurity(Server server) {
        LoginService loginService = new HashLoginService("default", JETTY_ROOT + "src/main/etc/realm.properties");
        server.addBean(loginService);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        server.setHandler(security);

        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"user", "admin"});


        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/cacheinfo");
        mapping.setConstraint(constraint);

        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);

        return security;
    }

    public static void main(String[] args) throws Exception{
        new CacheExecutor().start();

        Server server = new Server(SERVER_PORT);

        HandlerList handlerList = new HandlerList();

        ResourceHandler resources = new ResourceHandler();
        resources.setResourceBase(JETTY_ROOT + "src/main/webapp");
        handlerList.addHandler(resources);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SECURITY);
        context.addServlet(FreemarkerServlet.class, "/");

        context.addServlet(new ServletHolder(new CacheInfoServlet("")), "/cacheinfo");

        handlerList.addHandler(context);

        buildSecurity(server).setHandler(handlerList);

        server.start();
        server.join();
    }
}
