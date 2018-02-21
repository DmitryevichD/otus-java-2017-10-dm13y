package by.dm13y.study.services.message;

public class Address {
    private final Type type;
    public Address(Type type){
        this.type = type;
    }

    public enum Type{
        DB_SERVICE,
        WEB_SOCKET;
    }
}
