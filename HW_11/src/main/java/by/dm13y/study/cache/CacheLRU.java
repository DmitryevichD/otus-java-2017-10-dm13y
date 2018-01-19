package by.dm13y.study.cache;

import java.lang.ref.SoftReference;
import java.util.*;

public class CacheLRU<K, V> extends LinkedHashMap<K, SoftReference<V>> implements Cache<K,V>, CacheMXBean {
    private static final int DEFAULT_CAPACITY = 8_000;
    private static final long DEFAULT_IDLE_TIME = 10_000;
    private static final long DEFAULT_LIFE_TIME = 10_000;
    private int hits;
    private int miss;
    private int eviction;
    private final long timeToLiveMs;
    private final long timeToIdleMs;
    private final int entryCapacity;
    private final Map<K, TimeRate> timeValues;
    private final Calendar timeMaker = Calendar.getInstance();

    public CacheLRU(int entryCapacity, long timeToLiveMs, long timeToIdleMs){
        super(entryCapacity <= 0 ? DEFAULT_CAPACITY : entryCapacity, 0.75f, true);
        this.entryCapacity = entryCapacity <= 0 ? DEFAULT_CAPACITY : entryCapacity;
        this.timeToIdleMs = timeToIdleMs <= 0 ? DEFAULT_IDLE_TIME : timeToIdleMs;
        this.timeToLiveMs = timeToLiveMs <= 0 ? DEFAULT_LIFE_TIME : timeToLiveMs;
        timeValues = new HashMap<>(this.entryCapacity);
    }

    public CacheLRU(){
        this.entryCapacity = DEFAULT_CAPACITY;
        this.timeToIdleMs = DEFAULT_IDLE_TIME;
        this.timeToLiveMs = DEFAULT_LIFE_TIME;
        timeValues = new HashMap<>(this.entryCapacity);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        if(size() > entryCapacity){
            eviction++;
            return true;
        }
        return false;
    }

    @Override
    public int getHit() {
        return hits;
    }

    @Override
    public int getMiss() {
        return miss;
    }

    @Override
    public int getEviction() {
        return eviction;
    }

    @Override
    public int getCapacity() {
        return entryCapacity;
    }

    @Override
    public int getCacheSize(){
        return size();
    }

    @Override
    public long getTimeToLiveMs() {
        return timeToLiveMs;
    }

    @Override
    public long getTimeToIdleMs() {
        return timeToIdleMs;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public Date getTimeCreated(Long key) {
        try {
            return new Date(timeValues.get(key).life);
        }catch (Exception ex){
            return null;
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public Date getLastAccessTime(Long key) {
        try{
            return new Date(timeValues.get(key).idle);
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public V getFromCache(K key){
        SoftReference<V> sr = get(key);
        if(sr == null){
            miss++;
            return null;
        }
        V val = sr.get();
        if(val == null){
            miss++;
            remove(key);
            return null;
        }
        if (!timeIsCorrect(key)) {
            miss++;
            remove(key);
            timeValues.remove(key);
        }
        hits++;
        return val;
    }

    @Override
    public void remFromCache(K key) {
        remove(key);
        timeValues.remove(key);
    }

    private boolean timeIsCorrect(K key){
        TimeRate timeRate = timeValues.get(key);
        return timeRate == null ? false : timeRate.isActive();
    }

    @Override
    public void putToCache(K key, V value) {
        if (value != null) {
            SoftReference<V> sr = new SoftReference<>(value);
            put(key, sr);
            timeValues.put(key, new TimeRate());
        }
    }

    private class TimeRate{
        private long idle;
        private long life;

        public TimeRate(){
            idle = timeMaker.getTimeInMillis();
            life = timeMaker.getTimeInMillis();
        }

        public boolean isActive(){
            if (isAlive() && isIdle()){
                idle = timeMaker.getTimeInMillis();
                return true;
            }
            return false;
        }

        private boolean isAlive(){
            return (timeMaker.getTimeInMillis() - life) < timeToLiveMs;
        }

        private boolean isIdle(){
            return (timeMaker.getTimeInMillis() - idle) < timeToIdleMs;
        }
    }

    @Override
    public String toString() {
        return "Capacity:" + entryCapacity + "; " +
                "Size:" + size() + "; " +
                "Hits:" + hits + "; " +
                "Miss:" + miss + "; " +
                "Eviction:" + eviction;
    }
}
