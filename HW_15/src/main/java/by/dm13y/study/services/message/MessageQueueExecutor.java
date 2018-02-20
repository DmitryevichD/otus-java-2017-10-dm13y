package by.dm13y.study.services.message;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RecursiveAction;

public class MessageQueueExecutor extends RecursiveAction{
    private final ConcurrentLinkedQueue<Message> messages;
    private final MsgRecipient recipient;

    public MessageQueueExecutor(MsgRecipient recipient, ConcurrentLinkedQueue<Message> messages){
        this.messages = messages;
        this.recipient = recipient;
    }

    @Override
    protected void compute() {
        while (!messages.isEmpty()) {
            Message message = messages.poll();
            message.exec(recipient);
        }
    }
}
