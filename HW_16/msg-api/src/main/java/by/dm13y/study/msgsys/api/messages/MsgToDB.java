package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;

public class MsgToDB<T> extends Message<T>{

    public MsgToDB(Sender from, Integer to, T body, Long msgMarker){
        super(from, to, body, msgMarker);
    }

    @Override
    public T getBody() {
        return body;
    }

    @Override
    public String toString(){
        return "DB_" + super.toString();
    }
}
