package by.dm13y.study.msgsys.api.messages;

import by.dm13y.study.msgsys.api.Sender;

public class MsgSys<T> extends Message<T> {
    public MsgSys(Sender sender, Integer recipientId, T body, Operation id){
        super(sender, recipientId, body, id.value);
    }

    @Override
    public String toString(){
        return "SYS_" + super.toString();
    }

    @Override
    public T getSysInfo() {
        return body;
    }

    public enum Operation {
        DB_RECIPIENT_LIST(-1L);

        private final Long value;
        Operation(Long id){
            value = id;
        }

        public static Operation byId(Long id) {
            if (id == -1) {
                return Operation.DB_RECIPIENT_LIST;
            }
            throw new UnsupportedOperationException("operation not found");
        }

        public static Operation byMsg(Message msgSys){
            return byId(msgSys.getMsgMarker());
        }
    }
}
