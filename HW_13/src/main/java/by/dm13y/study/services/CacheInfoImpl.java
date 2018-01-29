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
    @Autowired
    private Gson gson;

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
