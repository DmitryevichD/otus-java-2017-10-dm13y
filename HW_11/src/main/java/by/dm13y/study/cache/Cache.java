package by.dm13y.study.cache;

public interface Cache<K, V> {
    void putToCache(K key, V value);
    V getFromCache(K key);
    void remFromCache(K key);
}
