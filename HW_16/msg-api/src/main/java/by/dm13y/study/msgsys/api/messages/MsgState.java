package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Header;
import lombok.Setter;

import java.util.Set;

public class MsgState extends Message {
    @Setter
    private Set<Header> availableHeaders;

    public MsgState(Header header, String msg){
        super(header, null);
    }
}
