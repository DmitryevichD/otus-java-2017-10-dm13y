package by.dm13y.study.services.message;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageContext {
    @Getter
    private final MessageCore messageCore;
    //todo: make addresses

    @Autowired
    public MessageContext(MessageCore messageCore) {
        this.messageCore = messageCore;
    }
}
