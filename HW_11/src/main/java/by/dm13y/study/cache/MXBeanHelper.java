package by.dm13y.study.cache;

import by.dm13y.study.cache.Cache;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class MXBeanHelper {

    public static void addCacheMXBean(Cache cache, ObjectName name){
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.registerMBean(cache, name);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
