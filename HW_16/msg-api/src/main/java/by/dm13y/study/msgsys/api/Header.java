package by.dm13y.study.msgsys.api;


import java.util.Date;

public class Header {
    public Header(MsgRecipient type){
        this.type = type;
    }
    private final Date dt = new Date();
    private Integer id;
    private MsgRecipient type;
}
