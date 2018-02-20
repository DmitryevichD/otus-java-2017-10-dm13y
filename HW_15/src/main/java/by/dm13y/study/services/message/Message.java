package by.dm13y.study.services.message;

public abstract class Message {
    private final Address from;
    private final Address to;

    public Message(Address from, Address to){
        this.from = from;
        this.to = to;
    }

    public abstract void exec(MsgRecipient MsgRecipient);
}
