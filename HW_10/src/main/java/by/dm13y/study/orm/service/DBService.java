package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;

import java.util.List;

public interface DBService{
    void save(Object entity);
    void remove(Object entity);
    void refresh(Object entity);
    void closeService();

    List<Address> getAddress(String street);
    Phone getPhone(String number);
    User getUser(long id);
}
