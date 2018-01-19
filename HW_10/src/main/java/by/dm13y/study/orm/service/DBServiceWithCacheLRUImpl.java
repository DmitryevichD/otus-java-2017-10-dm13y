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
    private final Cache<Long, Object> entityCache = new CacheLRU<>();
    private final Cache<String, Object> queryCache = new CacheLRU<>();

    public DBServiceWithCacheLRUImpl(){
        em  = Persistence.createEntityManagerFactory("by.dm13y.study.hw_10").createEntityManager();
    }

    @Override
    public void save(Object entity) {
        em.persist(entity);
        entityCache.putToCache(getEntityId(entity), entity);
    }

    @Override
    public void remove(Object entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
        entityCache.remFromCache(getEntityId(entity));
    }

    @Override
    public void refresh(Object entity) {
        em.refresh(entity);
        entityCache.putToCache(getEntityId(entity), entity);
    }

    @Override
    public void closeService() {
        if (em.isOpen() ){
            em.close();
        }
    }

    @Override
    public List<Address> getAddress(String street) {
        String id = getQueryId(Address.class, street);
        List<Address>  addressList = (List<Address> )queryCache.getFromCache(id);
        if(addressList != null){
            return addressList;
        }

        String query_str = "SELECT a FROM Address a WHERE a.street = :street";
        Query query = em.createQuery(query_str).setParameter("street", street);
        addressList = (List<Address>) query.getResultList();
        queryCache.putToCache(id, addressList);
        return addressList;
    }

    @Override
    public Phone getPhone(String number) {
        String id = getQueryId(Phone.class, number);
        Phone phone = (Phone)queryCache.getFromCache(id);
        if(phone != null){
            return phone;
        }

        String query_str = "SELECT p FROM Phone p WHERE p.number = :number";
        Query query = em.createQuery(query_str).setParameter("number", number);
        try {
            phone = (Phone) query.getSingleResult();
            queryCache.putToCache(id, phone);
            return phone;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getUser(long id) {
        User user = (User)entityCache.getFromCache(new Long(id));
        if (user == null) {
            em.getTransaction();
            user = em.find(User.class, id);
            entityCache.putToCache(getEntityId(user), user);
            return user;
        }
        return user;
    }

    private Long getEntityId(Object object){
        if (object == null) {
            return 0l;
        }
        if(object instanceof User){
            return ((User)object).getId();
        }
        if(object instanceof Address){
            return ((Address)object).getId();
        }
        if(object instanceof Phone){
            return ((Phone)object).getId();
        }
        return 0l;
    }

    public String getQuickEntityStatistic(){
        return entityCache.toString();
    }

    public String getQuickQueryStatistic(){
        return queryCache.toString();
    }

    private String getQueryId(Class clazz, String queryParam){
        return clazz.getName() + queryParam;
    }
}
