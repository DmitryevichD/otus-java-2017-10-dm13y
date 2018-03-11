package by.dm13y.study.msgsys.dbservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class StartApp {
    private final static Logger logger = LoggerFactory.getLogger(StartApp.class);
    private static Thread msgHandler;

    public static void main(String[] args) throws Exception{
        logger.info("Start db service");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DBMsgRecipient dbMsgRecipient = context.getBean(DBMsgRecipient.class);
        logger.info("connect to msg system");
        dbMsgRecipient.connect();
        if (dbMsgRecipient.isConnected()) {
            msgHandler = new Thread(dbMsgRecipient, "db msg handler");
            msgHandler.start();
            msgHandler.join();
        }
    }
}
