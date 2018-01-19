import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.User;
import by.dm13y.study.orm.service.DBService;
import by.dm13y.study.orm.service.DBServiceWithCache2LevelImpl;
import by.dm13y.study.orm.service.DBServiceWithCacheLRUImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static junit.framework.TestCase.assertNotSame;

public class HW_CacheLRUTest {
    private DBService service;

    @Before
    public void initService() throws Exception{
        service = new DBServiceWithCacheLRUImpl();
    }

    private String unicString(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }

    private long storeNewUser(){
        Address address = new Address(unicString("Address"));
        User user = new User(unicString("UserName"), 27, address);
        user.addPhone(unicString("Phone"));
        service.save(user);
        return user.getId();
    }

    private void getUserFromDB(long id){
        service.getUser(id);
    }

    @Test
    public void LRUCacheEntityTest() throws Exception {
        final int entityCapacity = 11_000;
        long[] entityIdArray = new long[entityCapacity];

        for (int i = 0; i < entityCapacity; i++) {
            entityIdArray[i] = storeNewUser();
        }

        String startValE = ((DBServiceWithCacheLRUImpl)service).getQuickEntityStatistic();

        Random random = new Random();
        for (int i = 0; i < entityCapacity; i++) {
            long randId = entityIdArray[random.nextInt(entityCapacity)];
            if(randId % 4 == 0){
                storeNewUser();
            }else {
                getUserFromDB(randId);
            }
        }

        System.out.println("\n\nENTITY_CACHE");
        System.out.println("BEFORE:" + startValE);
        System.out.println(" AFTER:" +((DBServiceWithCacheLRUImpl)service).getQuickEntityStatistic());
    }

}
