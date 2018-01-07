package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Department;
import by.dm13y.study.orm.entity.User;

import java.util.List;

public interface DBService {
    void add(Object obj);
    void remove(Object obj);
    void update(Object obj);

    Address getAddress(String street);

    List<User> getUsers();
    List<User> getUsersByAge(int age);
    List<User> getUsersByName(String name);
    List<User> getUserByDepartment(Department department);

}
