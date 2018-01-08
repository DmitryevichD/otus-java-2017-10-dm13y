package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Department;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;

import java.util.List;

public interface DBService {
    void save(Object obj);
    void remove(Object obj);

    Address getAddress(String street);
    Phone getPhone(String number);
    Department getDepartment(String name);


    List<User> getUsers();
    List<User> getUsersByAge(int age);
    List<User> getUsersByName(String name);
    List<User> getUserByDepartment(Department department);

}
