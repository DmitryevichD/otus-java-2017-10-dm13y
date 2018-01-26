package by.dm13y.study.web.model;

import by.dm13y.study.cache.CacheLRU;
import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.User;
import by.dm13y.study.orm.service.DBService;
import by.dm13y.study.orm.service.DBServiceWithCacheLRUImpl;

import javax.management.ObjectName;
import java.util.Random;
import java.util.UUID;

public class CacheExecutor extends Thread{

    private String unicString(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }

    private long storeNewUser(DBService service){
        Address address = new Address(unicString("Address"));
        User user = new User(unicString("UserName"), 27, address);
        user.addPhone(unicString("Phone"));
        service.save(user);
        return user.getId();
    }

    private void getUserFromDB(long id, DBService service){
        service.getUser(id);
    }

    private DBService initDBService(){
        try {
            return new DBServiceWithCacheLRUImpl(new ObjectName(CacheLRU.CACHE_MXBEAN_OBJ_NAME));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    public void run() {
        DBService service = initDBService();

        //copy-past from HW_CacheLRUTest (HW_10)
        final int entityCapacity = 11_000;
        long[] entityIdArray = new long[entityCapacity];


        for (int i = 0; i < entityCapacity; i++) {
            entityIdArray[i] = storeNewUser(service);
        }

        Random random = new Random();
        for (int i = 0; i < entityCapacity; i++)

        {
            long randId = entityIdArray[random.nextInt(entityCapacity)];
            if (randId % 4 == 0) {
                storeNewUser(service);
            } else {
                getUserFromDB(randId, service);
            }
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

    }
}
