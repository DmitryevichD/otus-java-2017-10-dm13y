package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Header;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
public abstract class Message implements Serializable {
    private Long toId;
    private Header from;

    public Message(Header from, Long toId){
        this.toId = toId;
        this.from = from;
    }
}
