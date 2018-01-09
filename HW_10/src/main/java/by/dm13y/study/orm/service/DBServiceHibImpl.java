package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;

import javax.persistence.*;
import java.util.List;

public class DBServiceHibImpl implements DBService{
    private final EntityManager em;

    public DBServiceHibImpl(){
        em  = Persistence.createEntityManagerFactory("by.dm13y.study.hw_10").createEntityManager();
    }

    @Override
    public void save(Object entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
    }

    @Override
    public void refresh(Object entity) {
        em.refresh(entity);
    }

    @Override
    public void remove(Object entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(entity);
        tx.commit();
    }

    @Override
    public List<Address> getAddress(String street) {
        String query_str = "SELECT a FROM Address a WHERE a.street = :street";
        Query query = em.createQuery(query_str).setParameter("street", street);
        return (List<Address>) query.getResultList();
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
    public User getUser(long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User user = em.find(User.class, id);
        tx.commit();
        return user;
    }

    public void closeService(){
        if (em.isOpen() ){
            em.close();
        }
    }
}
