package by.dm13y.study.msgsys.api.messages;


import by.dm13y.study.msgsys.api.Sender;

public class MsgException extends Message {
    public MsgException(Sender sender, Message sourceMessage, Throwable throwable){
        super(sender, sourceMessage.getSender().getId(), throwable, sourceMessage.getMsgMarker());
    }

    @Override
    public String toString(){
        return "EXC_" + super.toString();
    }

    @Override
    public Throwable getException() {
        return (Throwable)body;
    }
}
