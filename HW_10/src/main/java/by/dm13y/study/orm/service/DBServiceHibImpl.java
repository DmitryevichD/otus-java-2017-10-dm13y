package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;

import javax.persistence.*;

public class DBServiceHibImpl implements DBService{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public DBServiceHibImpl(){
        entityManagerFactory = Persistence.createEntityManagerFactory("by.dm13y.study.hw_10");
        openSession();
    }

    private void openSession(){
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
            ex.printStackTrace();
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
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(long id) {
        User user = null;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        user = em.find(User.class, id);
        tx.commit();
        return user;
    }
}
