package by.dm13y.study.orm.service;

import by.dm13y.study.cache.Cache;
import by.dm13y.study.cache.CacheLRU;
import by.dm13y.study.orm.entity.Address;
import by.dm13y.study.orm.entity.Phone;
import by.dm13y.study.orm.entity.User;

import javax.persistence.*;
import java.util.List;

public class DBServiceWithCacheLRUImpl implements DBService {
    private final EntityManager em;
    private final Cache<Object, Object> cache = new CacheLRU();

    public DBServiceWithCacheLRUImpl(){
        em  = Persistence.createEntityManagerFactory("by.dm13y.study.hw_10").createEntityManager();
    }

    @Override
    public void save(Object entity) {
        em.persist(entity);
        cache.putToCache(getCacheId(entity), entity);
    }

    @Override
    public void remove(Object entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
        cache.remFromCache(getCacheId(entity));
    }

    @Override
    public void refresh(Object entity) {
        em.refresh(entity);
        cache.putToCache(getCacheId(entity), entity);
    }

    @Override
    public void closeService() {
        if (em.isOpen() ){
            em.close();
        }
    }

    @Override
    public List<Address> getAddress(String street) {
        Long id = getCacheId(Address.class, street);
        List<Address>  addressList = (List<Address> )cache.getFromCache(id);
        if(addressList != null){
            return addressList;
        }

        String query_str = "SELECT a FROM Address a WHERE a.street = :street";
        Query query = em.createQuery(query_str).setParameter("street", street);
        addressList = (List<Address>) query.getResultList();
        cache.putToCache(id, addressList);
        return addressList;
    }

    @Override
    public Phone getPhone(String number) {
        Long id = getCacheId(Phone.class, number);
        Phone phone = (Phone)cache.getFromCache(id);
        if(phone != null){
            return phone;
        }

        String query_str = "SELECT p FROM Phone p WHERE p.number = :number";
        Query query = em.createQuery(query_str).setParameter("number", number);
        try {
            phone = (Phone) query.getSingleResult();
            cache.putToCache(id, phone);
            return phone;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getUser(long id) {
        User user = (User)cache.getFromCache(getCacheId(User.class, id));
        if (user == null) {
            em.getTransaction();
            user = em.find(User.class, id);
            cache.putToCache(getCacheId(user), user);
            return user;
        }
        return user;
    }

    private Long getCacheId(Object object){
        if (object == null) {
            return 0l;
        }
        if(object instanceof User){
            User usr = (User)object;
            return getCacheId(User.class, usr.getId());
        }

        //Caching is only an entity user, because receiving other entities does not implemented this services
        return 0l;
    }

    private Long getCacheId(Class clazz, Object value){
        long id = clazz.getClassLoader().hashCode();
        id <<= 32;
        return id | value.hashCode();
    }
}
