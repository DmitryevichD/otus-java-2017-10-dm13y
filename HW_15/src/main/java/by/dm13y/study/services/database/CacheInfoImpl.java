package by.dm13y.study.services.database;

import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MessageService;
import by.dm13y.study.services.message.MsgRecipient;
import by.dm13y.study.services.web.wsockets.MsgToCacheInfoWS;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CacheInfoImpl implements CacheInfo, MsgRecipient{
    private final Address address;
    private MessageService msgService;
    private Gson gson;

    @Autowired
    public void setGson(Gson gson){
        this.gson = gson;
    }

    @Autowired
    public void setContext(MessageService msgService) {
        this.msgService = msgService;
        this.msgService.addRecipient(this);
    }

    public CacheInfoImpl(){
        address = new Address(Address.Type.DB_SERVICE);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void getInfoByJson(Address address) {
        String cacheInfo = jsonCacheInfo();
        Message message = new MsgToCacheInfoWS(getAddress(), address, cacheInfo);
        msgService.sendMessage(message);
    }

    private String attrVal(String name) {
        return String.valueOf(new Random().nextInt(2302));
    }

    public String jsonCacheInfo(){
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
