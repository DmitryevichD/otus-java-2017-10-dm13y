package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Department;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

public class DBServiceHibImpl implements DBService{
    @PersistenceContext(unitName = "by.dm13y.study.hw_10")
    private EntityManager em;
    public DBServiceHibImpl(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("by.dm13y.study.hw_10");
        em = entityManagerFactory.createEntityManager();
    }

    @Override
    public void save(Object entity) {
        if (entity.getClass().getAnnotation(Entity.class) == null){
            throw new UnsupportedOperationException("Object is not persistence object");
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
    }


    @Override
    public void remove(Object entity) {
        if (entity.getClass().getAnnotation(Entity.class) == null){
            throw new UnsupportedOperationException("Object is not persistence object");
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(entity);
        tx.commit();
    }

    @Override
    public Address getAddress(String street) {
        String query_str = "SELECT a FROM Address a WHERE a.street = :street";
        Query query = em.createQuery(query_str).setParameter("street", street);
        try {
            return (Address)query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Phone getPhone(String number) {
        String query_str = "SELECT p FROM Phone p WHERE p.number = :number";
        Query query = em.createQuery(query_str).setParameter("number", number);
        try {
            return (Phone) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Department getDepartment(String name) {
        String query_str = "SELECT d FROM Department d WHERE d.name = :name";
        Query query = em.createQuery(query_str).setParameter("name", name);
        try {
            return (Department) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }


    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public List<User> getUsersByAge(int age) {
        return null;
    }

    @Override
    public List<User> getUsersByName(String name) {
        String query_str = "SELECT u FROM User u WHERE u.name = :name";
        Query query = em.createQuery(query_str).setParameter("name", name);
        try {
            return (List<User>) query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<User> getUserByDepartment(Department department) {
        return null;
    }


    public void updUser(User user) {

    }
}
