package by.dm13y.study.cache;

import javax.management.MXBean;
import java.util.Date;

@MXBean
public interface CacheMXBean<K> {
    int getHit();
    int getMiss();
    int getEviction();
    int getCapacity();
    int getCacheSize();
    long getTimeToLiveMs();
    long getTimeToIdleMs();
    Date getTimeCreated(Long key);
    Date getLastAccessTime(Long key);
}
