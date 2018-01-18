package by.dm13y.study.orm.service;

import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.loader.plan.spi.EntityFetch;

import javax.persistence.*;
import java.util.List;

public class DBServiceWithCache2LevelImpl implements DBService {
    private final EntityManagerFactory emf;
    public DBServiceWithCache2LevelImpl(){
        emf = Persistence.createEntityManagerFactory("by.dm13y.study.hw_11");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void save(Object entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Object entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public void refresh(Object entity) {
        getEntityManager().refresh(entity);
    }

    @Override
    public void closeService() {
        throw new UnsupportedOperationException("In this implementation this method is not supported");
    }

    @Override
    public List<Address> getAddress(String street) {
        String query_str = "SELECT a FROM Address a WHERE a.street = :street";
        Query query = getEntityManager().createQuery(query_str).setParameter("street", street);
        return (List<Address>) query.getResultList();
    }

    @Override
    public Phone getPhone(String number) {
        String query_str = "SELECT p FROM Phone p WHERE p.number = :number";
        Query query = getEntityManager().createQuery(query_str).setParameter("number", number);
        try {
            return (Phone) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getUser(long id) {
        return getEntityManager().getReference(User.class, id);
    }
}
