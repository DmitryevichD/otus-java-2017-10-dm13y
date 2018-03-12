package by.dm13y.study.msgsys.dbservice;

import by.dm13y.study.msgsys.api.SenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartApp {
    private final static Logger logger = LoggerFactory.getLogger(StartApp.class);

    public static void main(String[] args) throws Exception{
        logger.info("Start db service");
        DBMsgRecipient dbMsgRecipient = new DBMsgRecipient("", SenderType.DB_SERVICE);
        logger.info("connect to msg system");
        dbMsgRecipient.connect();
        if (dbMsgRecipient.isConnected()) {
            Thread msgHandler = new Thread(dbMsgRecipient, "DB msg handler");
            msgHandler.start();
            msgHandler.join();
        }
    }
}
