package by.dm13y.study.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("defaultMessageContext")
public class MessageContext {
    private final MessageCore messageCore;

    @Autowired
    public MessageContext(MessageCore messageCore) {
        this.messageCore = messageCore;
    }

    public void addRecepient(MsgRecipient recipient) {
        messageCore.addRecipient(recipient);
    }

    public void sendMessage(Message message){
        messageCore.sendMessage(message);
    }
}
