package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;


public interface DBService {
    void save(Object obj);
    void remove(Object obj);

    Address getAddress(String street);
    Phone getPhone(String number);
    User getUser(long id);

}
