package by.dm13y.study.web;

import by.dm13y.study.web.servlets.CacheInfo;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class WebServer {
    private final static int SERVER_PORT = 8080;
    private final static String JETTY_ROOT = "HW_12/";


    private static ConstraintSecurityHandler buildSecurity(Server server) {
        LoginService loginService = new HashLoginService("default", JETTY_ROOT + "src/main/etc/realm.properties");
        server.addBean(loginService);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        server.setHandler(security);

        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"user", "guest"});


        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/cacheinfo");
        mapping.setConstraint(constraint);

        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);

        return security;
    }

    private static ConstraintSecurityHandler buildFormSecurity(Server server) {
        LoginService loginService = new HashLoginService("default", JETTY_ROOT + "src/main/etc/realm.properties");
        server.addBean(loginService);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        server.setHandler(security);

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__FORM_AUTH);
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"admin", "user"});


        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);

        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new FormAuthenticator("/login", "/login", true));
        security.setLoginService(loginService);

        return security;
    }

    //todo: add FORM AUTHENTICATOR

    public static void main(String[] args) throws Exception{
        Server server = new Server(SERVER_PORT);

        HandlerList handlerList = new HandlerList();

        ResourceHandler resources = new ResourceHandler();
        resources.setResourceBase(JETTY_ROOT + "src/main/webapp");
        handlerList.addHandler(resources);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SECURITY);
        context.addServlet(CacheInfo.class, "/cacheinfo");
        context.addServlet(new ServletHolder(new DefaultServlet() {
            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.getWriter().append("<html><form method='POST' action='/j_security_check'>"
                        + "<input type='text' name='j_username'/>"
                        + "<input type='password' name='j_password'/>"
                        + "<input type='submit' value='Login'/></form></html>");
            }
        }), "/login");

        handlerList.addHandler(context);

        buildFormSecurity(server).setHandler(handlerList);
//        server.setHandler(handlerList);

        server.start();
        server.join();
    }
}
