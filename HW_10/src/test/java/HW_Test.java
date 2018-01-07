import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.User;
import by.dm13y.study.orm.service.DBService;
import by.dm13y.study.orm.service.DBServiceHibImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class HW_Test {
    private static DBService dbService;

    @BeforeClass
    public static void entityManager(){
        dbService = new DBServiceHibImpl();
    }

    @Test
    public void addressTest(){
        String tst_address = "test_address";
        assertNull(dbService.getAddress(tst_address));

        Address address = new Address(tst_address);
        dbService.add(address);
        Address store_address = dbService.getAddress(tst_address);
        assertEquals(store_address.getStreet(), tst_address);

        dbService.remove(store_address);
        assertNull(dbService.getAddress(tst_address));
    }
}
