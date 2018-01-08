import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Department;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;
import by.dm13y.study.orm.service.DBService;
import by.dm13y.study.orm.service.DBServiceHibImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class HW_Test {
    private static DBService dbService;
    private PersistenceUtil util = Persistence.getPersistenceUtil();

    @BeforeClass
    public static void entityManager(){
        dbService = new DBServiceHibImpl();
    }

    @Test
    public void HW_10Test() throws Exception{
        final String userName = "test_user";
        final String streetName = "test_street";
        final String phone1 = "number1";
        final String phone2 = "number2";
        final String department = "default_department";

        Address address = new Address(streetName);
        Phone phoneOne = new Phone(phone1);
        Phone phoneTwo = new  Phone(phone2);
        Department dep = new Department(department);




        User user = new User(userName, 27,
                address,
                phoneOne,
                dep);

        dbService.save(user);

        assertTrue(util.isLoaded(address));
        assertTrue(util.isLoaded(phoneOne));
        assertTrue(util.isLoaded(phoneTwo));
        assertTrue(util.isLoaded(dep));
        assertTrue(util.isLoaded(user));

        assertTrue(util.isLoaded(user));

        assertEquals(dbService.getAddress(streetName).getStreet(), streetName);
        assertEquals(dbService.getPhone(phone1).getNumber(), phone1);
        assertNull(dbService.getPhone(phone2));

        user.getPhones().add(new Phone(phone2));
        dbService.save(user);
        assertEquals(dbService.getPhone(phone2).getNumber(), phone2);

        List<User> users = dbService.getUsersByName(userName);
        assertFalse(users.isEmpty());
        users.forEach(usr -> assertTrue(usr.getPhones().size() == 2));
        users.forEach(usr -> assertTrue(usr.getDep().getName().equals(department)));


        Department depFromDB = dbService.getDepartment(department);
        assertEquals(depFromDB.getName(), department);
        List<User> depUsers = depFromDB.getUsers();

        assertFalse(depUsers.isEmpty());
//        assertTrue(dbService.getDepartment(department).getUsersList().contains(user));



    }

//    @Test
//    public void phoneTest(){
//        String tst_phone = "test_phone";
//        assertNull(dbService.getAddress(tst_phone));
//
//        Phone phone = new Phone(tst_phone);
//        dbService.save(phone);
//        Phone store_phone = dbService.getPhone(tst_phone);
//        assertEquals(store_phone.getNumber(), tst_phone);
//
//        dbService.remove(tst_phone);
//        assertNull(dbService.getPhone(tst_phone));
//    }
}
