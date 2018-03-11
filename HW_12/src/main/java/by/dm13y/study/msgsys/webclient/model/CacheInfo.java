package by.dm13y.study.msgsys.webclient.model;

import by.dm13y.study.cache.CacheLRU;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class CacheInfo {
    private static MBeanServer mBeanServer;
    private static ObjectName objectName;

    static {
        mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            objectName = new ObjectName(CacheLRU.CACHE_MXBEAN_OBJ_NAME);
        }catch (Exception ex){
            throw new RuntimeException();
        }

    }

    private CacheInfo(){}

    private static String attrVal(String name) {
        try {
            return ManagementFactory.getPlatformMBeanServer().getAttribute(objectName, name).toString();
        } catch (Exception ex) {
            return "not found";
        }
    }

    public static String getHit(){
        return attrVal("Hit");
    }

    public static String getMis(){
        return attrVal("Miss");
    }

    public static String getEviction(){
        return attrVal("Eviction");
    }

    public static String getCapacity(){
        return attrVal("Capacity");
    }

    public static String getCacheSize(){
        return attrVal("CacheSize");
    }

    public static String getTimeToIdleMs(){
        return attrVal("TimeToIdleMs");
    }

    public static String getTimeToLiveMs(){
        return attrVal("TimeToLiveMs");
    }
}
