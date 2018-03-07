package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Header;
import by.dm13y.study.msgsys.api.MsgRecipient;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
public abstract class Message implements Serializable {
    private Long toId;
    private Header from;
    private Set<Header> state;

    public Message(Header from, Long toId){
        this.toId = toId;
        this.from = from;
    }

    public void setState(Set<Header> state) {
        this.state = state;
    }
}
