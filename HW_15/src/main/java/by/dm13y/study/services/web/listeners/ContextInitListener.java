package by.dm13y.study.services.web.listeners;

import by.dm13y.study.config.AppConfig;
import by.dm13y.study.services.message.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent mc) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        ac.getBean(MessageService.class).start();
    }
}
