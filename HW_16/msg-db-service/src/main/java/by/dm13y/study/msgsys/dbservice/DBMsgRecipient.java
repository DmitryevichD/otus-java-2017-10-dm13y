package by.dm13y.study.msgsys.dbservice;

import by.dm13y.study.msgsys.api.*;
import by.dm13y.study.msgsys.api.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.Thread.sleep;

class DBMsgRecipient extends MsgRecipient{
    private final static Logger logger = LoggerFactory.getLogger(DBMsgRecipient.class);
    private final DBCacheInformer cacheInformer = new DBCacheInformerImpl();

    public DBMsgRecipient(String msgSysHost, SenderType recipientType) {
        super(msgSysHost, recipientType);
    }

    @Override
    public void handleSysMsg(Message msg){
        logger.debug("execute handler for state msg");
    }

    @Override
    public void handleExceptionMsg(Message msg) {
        logger.error("receive exception", msg.getException());
        throw new UnsupportedOperationException(msg.getException());
    }

    @Override
    public void handleReceiveMsg(Message msg) {
        Message response;
        if (msg instanceof MsgToDB) {
            MsgToDB msgToDB = ((MsgToDB) msg);
            try {
                String body = (String) msgToDB.getBody();
                if (body.equals("cacheInfo")) {
                    String cacheInfo = cacheInformer.getCacheInfo();
                    response = MessageHelper.buildResponse(sender, msg, cacheInfo);
                } else {
                    logger.error(body + " command is not supported");
                    response = new MsgException(sender, msg, new UnsupportedOperationException("db -> command is not supported"));
                }
            } catch (Exception ex) {
                response = new MsgException(sender, msg, ex);
                logger.error("WTF", ex);
            }
            sendMsg(response);
        }else {
            response = new MsgException(sender, msg, new UnsupportedOperationException(msg.getClass() + " msg type unsupported"));
            sendMsg(response);
            logger.error("msg " + msg.getClass() + " msg type unsupported");
        }

    }
}
