package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;

public class MsgToWS<T> extends Message<T> {

    public MsgToWS(Sender from, Integer toId, T body, Long customId) {
        super(from, toId, body, customId);
    }

    @Override
    public T getBody() {
        return body;
    }
    @Override
    public String toString(){
        return "WC_" + super.toString();
    }
}
