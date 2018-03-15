package by.dm13y.study.msgsys.webclient.websockets;

import by.dm13y.study.msgsys.webclient.msg.MsgClient;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CacheInfoWS extends TextWebSocketHandler {
    private final MsgClient msgClient = new MsgClient();
    private final ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(5);

    @PostConstruct
    public void init(){
        msgClient.connect();
        msgClient.getDBRecipientsList();
        CompletableFuture.runAsync(() ->
                scheduledService.scheduleWithFixedDelay(msgClient, 100, 100, TimeUnit.MILLISECONDS));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        msgClient.getCacheInfoFromDB(session);
    }

    @PreDestroy
    public void destroy(){
        scheduledService.shutdown();
    }
}
