package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;

public class MsgToDB<T> extends Message{

    public MsgToDB(Sender from, Integer to, T body, Long msgMarker){
        super(from, to, body, msgMarker);
    }

    @Override
    public String toString(){
        return "DB_" + super.toString();
    }
}
