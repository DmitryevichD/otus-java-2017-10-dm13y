package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public abstract class Message<T> implements Serializable {
    private final Integer recipientId;
    private final Sender sender;
    private final T body;
    @Setter
    private Long msgMarker;

    public Message(Sender sender, Integer recipientId, T body, Long msgMarker){
        this.recipientId = recipientId;
        this.sender = sender;
        this.body = body;
        this.msgMarker = msgMarker;
    }
}
