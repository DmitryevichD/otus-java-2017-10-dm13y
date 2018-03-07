package by.dm13y.study.msgsys.api.messages;


public class MsgException extends Message {
    private Throwable throwable;

    public MsgException(Message sourceMsg, Throwable throwable){
        super(sourceMsg.getFrom(), sourceMsg.getToId());
        this.throwable = throwable;
    }
}
