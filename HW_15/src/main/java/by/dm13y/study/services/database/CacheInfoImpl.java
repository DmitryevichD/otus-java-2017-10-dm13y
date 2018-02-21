package by.dm13y.study.services.database;

import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MessageContext;
import by.dm13y.study.services.message.MsgRecipient;
import by.dm13y.study.services.web.wsockets.MsgToCacheInfoWS;
import by.dm13y.study.services.web.wsockets.WebSocketMsgr;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;

@Service
public class CacheInfoImpl implements CacheInfo, MsgRecipient{
    private final Address address;
    private MBeanServer mBeanServer;
    private ObjectName objectName;
    private Gson gson;

    @Autowired
    public void setmBeanServer(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    @Autowired
    public void setObjectName(ObjectName objectName){
        this.objectName = objectName;
    }

    @Autowired
    public void setGson(Gson gson){
        this.gson = gson;
    }

    public CacheInfoImpl(){
        address = new Address(Address.Type.DB_SERVICE);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getInfoByJson() {
        Message message = new MsgToCacheInfoWS() {
            @Override
            public void exec(WebSocketMsgr webSocketMsgr) {
                webSocketMsgr.sendMsgToClient(toJson());
            }
        };
    }

    private String attrVal(String name) {
        try {
            return mBeanServer.getAttribute(objectName, name).toString();
        } catch (Exception ex) {
            return "not found";
        }
    }

    public String toJson(){
        return gson.toJson(new CacheInfo());
    }

    private class CacheInfo{
        private String hit = attrVal("Hit");
        private String miss = attrVal("Miss");
        private String eviction = attrVal("Eviction");
        private String capacity = attrVal("Capacity");
        private String cacheSize = attrVal("CacheSize");
        private String timeToIdle = attrVal("TimeToIdleMs");
        private String timeToLive = attrVal("TimeToLiveMs");
    }
}
