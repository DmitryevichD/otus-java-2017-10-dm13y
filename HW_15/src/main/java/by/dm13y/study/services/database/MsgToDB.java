package by.dm13y.study.services.database;

import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MsgRecipient;

public class MsgToDB extends Message {
    private DbMsgType dbMsgType;
    public MsgToDB(Address from, Address to, DbMsgType msgType) {
        super(from, to);
        this.dbMsgType = msgType;
    }

    @Override
    public void exec(MsgRecipient msgRecipient) {
        if (msgRecipient instanceof CacheInfo){
            exec((CacheInfo)msgRecipient);
        }

    }

    public void exec(CacheInfo cacheInfo){
        if(dbMsgType == DbMsgType.CACHE_INFO) {
            cacheInfo.getInfoByJson(getFrom());
        }
    }

    public enum DbMsgType {
        CACHE_INFO
    }
}
