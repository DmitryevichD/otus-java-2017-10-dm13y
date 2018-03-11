package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;

public class MsgSys<T> extends Message {
    public MsgSys(Sender sender, Integer recipientId, T body, Operation id){
        super(sender, recipientId, body, id.value);
    }

    public enum Operation {
        RECIPIENT_LIST(-1l);

        private Long value;
        private Operation(Long id){
            value = id;
        }

        public static Operation byId(Long id) {
            if (id == -1) {
                return Operation.RECIPIENT_LIST;
            }
            throw new UnsupportedOperationException("operation not found");
        }

        public static Operation byMsg(MsgSys msgSys){
            return byId(msgSys.getMsgMarker());
        }
    }
}
