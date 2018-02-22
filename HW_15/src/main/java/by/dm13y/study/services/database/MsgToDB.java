package by.dm13y.study.services.database;

import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MsgRecipient;

public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(MsgRecipient msgRecipient) {
        if (msgRecipient instanceof CacheInfo){
            exec((CacheInfo)msgRecipient);
        }

    }

    public abstract void exec(CacheInfo cacheInfo);
}
