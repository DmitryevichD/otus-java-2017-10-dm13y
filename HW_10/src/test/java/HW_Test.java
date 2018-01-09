import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;
import by.dm13y.study.orm.service.DBService;
import by.dm13y.study.orm.service.DBServiceHibImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HW_Test {

    @Test
    public void HW_10Test() throws Exception {
        DBService dbService = new DBServiceHibImpl();
        final String userName = "strUsername";
        final String streetName = "strStreet";
        final String phone1 = "strNumber1";
        final String phone2 = "strNumber2";
        final String phone3 = "strNumber3";

        Address address = new Address(streetName);

        User user = new User(userName, 27, address);
        user.addPhone(phone1);
        user.addPhone(phone2);
        dbService.save(user);

        Phone phoneTree = new Phone(phone3, user);
        dbService.save(phoneTree);
        assertEquals("Check address", dbService.getAddress(streetName).getStreet(), streetName);

        Phone usr_phoneOne = dbService.getPhone(phone1);
        assertEquals("check phone 1", usr_phoneOne.getNumber(), phone1);
        assertEquals("check user name in phone1", usr_phoneOne.getUser().getName(), userName);

        Phone usr_phoneTwo = dbService.getPhone(phone2);
        assertEquals("check phone 2", usr_phoneTwo.getNumber(), phone2);
        assertEquals("check user name in phone2", usr_phoneTwo.getUser().getName(), userName);

        Phone usr_phoneTree = dbService.getPhone(phone3);
        assertEquals("check phone 2", usr_phoneTree.getNumber(), phone3);
        assertEquals("check user name in phone2", usr_phoneTree.getUser().getName(), userName);

        Set<Phone> phones = dbService.getUser(user.getId()).getPhones();
        assertFalse(phones.isEmpty());

        List<String> phoneList = phones.stream()
                .map(ph -> ph.getNumber())
                .collect(Collectors.toList());

        assertTrue(phoneList.contains(phone1));
        assertTrue(phoneList.contains(phone2));
        //todo:flush or open - close entity manger (em scope)
        assertTrue(phoneList.contains(phone3));
    }
}

