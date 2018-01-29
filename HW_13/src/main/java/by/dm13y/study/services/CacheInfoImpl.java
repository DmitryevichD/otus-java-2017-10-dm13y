package by.dm13y.study.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.management.MBeanServer;
import javax.management.ObjectName;

@Service
public class CacheInfoImpl implements CacheInfo {
    @Autowired
    private MBeanServer mBeanServer;
    @Autowired
    private ObjectName objectName;

    private Gson gson = new Gson();

    public CacheInfoImpl(){}

    private String attrVal(String name) {
        try {
            return mBeanServer.getAttribute(objectName, name).toString();
        } catch (Exception ex) {
            return "not found";
        }
    }

    @Override
    public String getHit(){
        return attrVal("Hit");
    }
    @Override
    public String getMis(){
        return attrVal("Miss");
    }
    @Override
    public String getEviction(){
        return attrVal("Eviction");
    }
    @Override
    public String getCapacity(){
        return attrVal("Capacity");
    }
    @Override
    public String getCacheSize(){
        return attrVal("CacheSize");
    }
    @Override
    public String getTimeToIdleMs(){
        return attrVal("TimeToIdleMs");
    }
    @Override
    public String getTimeToLiveMs(){
        return attrVal("TimeToLiveMs");
    }

    public String toJson(){
        return gson.toJson(new CacheInfo());
    }

    private class CacheInfo{
        private String hit = getHit();
        private String miss = getMis();
        private String eviction = getEviction();
        private String capacity = getCapacity();
        private String cacheSize = getCacheSize();
        private String timeToIdle = getTimeToIdleMs();
        private String timeToLive = getTimeToLiveMs();


    }
}
