package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public abstract class Message<T> implements Serializable {
    @Getter
    private final Integer recipientId;
    @Getter
    private final Sender sender;
    protected final T body;
    @Getter @Setter
    private Long msgMarker;

    Message(Sender sender, Integer recipientId, T body, Long msgMarker){
        this.recipientId = recipientId;
        this.sender = sender;
        this.body = body;
        this.msgMarker = msgMarker;
    }

    @Override
    public String toString(){
        return "MSG:(" + sender + " to: " + recipientId + " marker: " + msgMarker + ")";
    }

    public T getSysInfo(){return null;}
    public Throwable getException(){return null;};
    public T getBody(){return null;}
}
