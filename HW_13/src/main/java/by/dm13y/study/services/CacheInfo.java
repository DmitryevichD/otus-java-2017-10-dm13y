package by.dm13y.study.services;

public interface CacheInfo {
    String getHit();

    String getMis();

    String getEviction();

    String getCapacity();

    String getCacheSize();

    String getTimeToIdleMs();

    String getTimeToLiveMs();
}
