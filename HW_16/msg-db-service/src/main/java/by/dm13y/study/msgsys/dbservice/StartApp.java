package by.dm13y.study.msgsys.dbservice;

import by.dm13y.study.msgsys.api.SenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class StartApp {
    private final static Logger logger = LoggerFactory.getLogger(StartApp.class);

    public static void main(String[] args) throws Exception{
        logger.info("Start db service");
        DBMsgRecipient dbMsgRecipient = new DBMsgRecipient("", SenderType.DB_SERVICE);
        logger.info("connect to msg system");
        dbMsgRecipient.connect();

        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(2);
        ScheduledFuture<?> schedule = scheduledService.scheduleWithFixedDelay(dbMsgRecipient, 100, 100, TimeUnit.MILLISECONDS);
    }
}
