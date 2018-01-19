import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.User;
import by.dm13y.study.orm.service.DBService;
import by.dm13y.study.orm.service.DBServiceWithCache2LevelImpl;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class HW_CacheTest {
    private final DBService service = new DBServiceWithCache2LevelImpl();

    @Test
    public void CacheTest() throws Exception {
        final String userName = "strUsername";
        final String streetName = "strStreet";
        final String phone1 = "strNumber1";
        Address address = new Address(streetName);

        User user = new User(userName, 27, address);
        user.addPhone(phone1);
        service.save(user);

        User user1 = service.getUser(user.getId());
        assertNotSame(user, user1);

        assertEquals(user.getId(), user1.getId());
        assertEquals(user.getName(), user1.getName());
    }
}
