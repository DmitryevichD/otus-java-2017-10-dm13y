import by.dm13y.study.cache.Cache;
import by.dm13y.study.cache.CacheLRU;
import by.dm13y.study.cache.MXBeanHelper;
import org.junit.Before;
import org.junit.Test;

import javax.management.ObjectName;
import java.util.Random;

public class HW_Test {
    private Cache<Long, SimpleUserEntity> cache;
    @Before
    public void initCache() throws Exception{
        cache = new CacheLRU<>();
        MXBeanHelper.addCacheMXBean(cache, new ObjectName("by.dm13y.study.cache:type=CacheLRU"));
    }

    @Test
    public void justWork(){
        Random random = new Random();
        final int USER_COUNTS = 100_000;
        SimpleUserEntity[] userDB = new  SimpleUserEntity[USER_COUNTS];

        for (int i = 0; i < USER_COUNTS; i++) {
            SimpleUserEntity simpleUserEntity = new SimpleUserEntity(i, "name:" + 1, random.nextInt(90), "address:" + 1);
            userDB[i] = simpleUserEntity;

            //warm up
//            cache.putToCache((long)i, simpleUserEntity);
        }

        int fromCache = 0;
        int fromDB = 0;
        for (int i = 0; i < 1_000_000; i++) {
            int key = random.nextInt(USER_COUNTS);
            SimpleUserEntity sue = cache.getFromCache((long)key);
            if (sue == null) {
                sue = userDB[key];
                cache.putToCache((long)key, sue);
                fromDB++;
            }else {
                //value is taken from the cache
                fromCache++;
            }
        }

        System.out.println("From DB:" + fromDB + "<------>From Cache:" + fromCache);
    }
}
