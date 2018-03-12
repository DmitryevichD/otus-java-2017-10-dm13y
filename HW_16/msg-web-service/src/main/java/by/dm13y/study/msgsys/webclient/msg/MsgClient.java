package by.dm13y.study.msgsys.webclient.msg;

import by.dm13y.study.msgsys.api.MsgRecipient;
import by.dm13y.study.msgsys.api.SenderType;
import by.dm13y.study.msgsys.api.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class MsgClient extends MsgRecipient implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(MsgClient.class);
    private List<Integer> dbServiceList = new ArrayList<>();
    private final AtomicLong msgIdGenerator = new AtomicLong();
    private final Map<Long, WebSocketSession> localSenders = new ConcurrentHashMap<>();
    public MsgClient() {
        super("", SenderType.WEB_SOCKET);
    }

    @Override
    public void handleSysMsg(MsgSys msgSys, MsgSys.Operation operId) {
        if(operId == MsgSys.Operation.DB_RECIPIENT_LIST){
            //noinspection unchecked
            dbServiceList = (List<Integer>)msgSys.getBody();
        }
    }

    @Override
    public void handleExceptionMsg(MsgException msgException) {
        logger.error("msg exception", msgException);
    }

    @Override
    public void handleReceiveMsg(Message msg) {
        if (msg instanceof MsgToWS) {
            Long msgId = msg.getMsgMarker();
            WebSocketSession session = localSenders.get(msgId);
            if (session != null) {
                MsgToWS msgToWS = ((MsgToWS) msg);
                String cacheInfo = (String)msgToWS.getBody();
                try {
                    session.sendMessage(new TextMessage(cacheInfo));
                } catch (IOException e) {
                    logger.error("send msg error", e);
                }
                removeHandler(msgId);
            }
        }else {
            logger.error("Unsupported message");
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void removeHandler(Long id) {
        localSenders.remove(id);
    }

    @SuppressWarnings("unchecked")
    public void getCacheInfoFromDB(WebSocketSession session) {
        if (dbServiceList.isEmpty()) {
            getDBRecipientsList();
        }
        if (dbServiceList.isEmpty()) {
            logger.info("database services is not connected to msg-system");
            return;
        }
        MsgToDB msgToDB = new MsgToDB(sender, dbServiceList.get(0), "cacheInfo", msgIdGenerator.incrementAndGet());
        getCacheInfo(msgToDB, session);
    }

    private void getCacheInfo(Message msg, WebSocketSession session) {
        localSenders.put(msg.getMsgMarker(), session);
        sendMsg(msg);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                getMsg();
                Thread.sleep(30);
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                logger.info("thread is interrupt");
                Thread.currentThread().interrupt();
            }
        }
    }
}
