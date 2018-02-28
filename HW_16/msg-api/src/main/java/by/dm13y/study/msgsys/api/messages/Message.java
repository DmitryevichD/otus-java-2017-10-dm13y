package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Header;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private Header header;
    private String msg;
    public Message(Header header, String msg){
        this.header = header;
        this.msg = msg;
    }
}
