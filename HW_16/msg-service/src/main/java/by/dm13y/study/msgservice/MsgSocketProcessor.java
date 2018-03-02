package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.Header;
import by.dm13y.study.msgsys.api.messages.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MsgSocketProcessor extends Thread {
    private final MsgQueue msgQueue;
    private final long PROCESSING_DELAY;
    public MsgSocketProcessor(MsgQueue msgQueue, long processing_delay) {
        this.msgQueue = msgQueue;
        this.PROCESSING_DELAY = processing_delay;
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            Map<Header, MsgSocketWrapper> recipientMap = msgQueue.getRecipientMap();
            for (Map.Entry<Header, MsgSocketWrapper> socket : recipientMap.entrySet()) {
                CompletableFuture.runAsync(() -> {
                    List<Message> messages = socket.getValue().readMessage();
                    if (!messages.isEmpty()) {
                        msgQueue.getRecipientQueue().get(socket.getKey()).addAll(messages);
                    }
                });
            }
            try {
                sleep(PROCESSING_DELAY);
            } catch (InterruptedException e) { }
        }
    }

}
