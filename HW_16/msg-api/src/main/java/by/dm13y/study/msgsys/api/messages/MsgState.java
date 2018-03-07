package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Header;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class MsgState extends Message {
    @Setter @Getter
    private Set<Header> availableHeaders;

    public MsgState(Header header){
        super(header, null);
    }
}
