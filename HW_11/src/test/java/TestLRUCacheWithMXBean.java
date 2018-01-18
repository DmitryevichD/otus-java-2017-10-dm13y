
import by.dm13y.study.cache.Cache;
import by.dm13y.study.cache.CacheLRU;
import by.dm13y.study.cache.MXBeanHelper;
import org.junit.Test;

import javax.management.ObjectName;

public class TestLRUCacheWithMXBean {
    @Test
    public void LRUCache() throws Exception{
        Cache<Long, Integer> cache = new CacheLRU<>(10, 500, 500);

        MXBeanHelper.addCacheMXBean(cache, new ObjectName("by.dm13y.study.cache:type=CacheLRU"));

        for (long i = 0; i < 10000; i++) {
            cache.putToCache(i, new Integer(100 * (int)i));
            Thread.sleep(100);
        }
    }

    @Test
    public void LRUCacheWithDefConstructor() throws Exception{
        Cache<Long, Integer> cache = new CacheLRU<>();

        MXBeanHelper.addCacheMXBean(cache, new ObjectName("by.dm13y.study.cache:type=CacheLRU"));

        for (long i = 0; i < 10000; i++) {
            cache.putToCache(i, new Integer(100 * (int)i));
            Thread.sleep(100);
        }
    }
}
