package by.dm13y.study.services;

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
}
