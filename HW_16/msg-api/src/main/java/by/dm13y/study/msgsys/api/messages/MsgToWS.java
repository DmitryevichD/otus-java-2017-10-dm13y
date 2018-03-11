package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;

public class MsgToWS<T> extends Message {

    public MsgToWS(Sender from, Integer toId, T body, Long customId) {
        super(from, toId, body, customId);
    }
}
