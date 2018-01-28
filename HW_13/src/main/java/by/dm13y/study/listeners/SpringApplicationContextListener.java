package by.dm13y.study.listeners;

import by.dm13y.study.config.AppContext;
import by.dm13y.study.services.CacheExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SpringApplicationContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new CacheExecutor().start();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppContext.class);
        sce.getServletContext().setAttribute("applicationContext", ac);
    }
}
