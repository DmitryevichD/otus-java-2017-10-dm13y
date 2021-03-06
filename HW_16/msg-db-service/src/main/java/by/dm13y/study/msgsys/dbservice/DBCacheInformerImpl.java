package by.dm13y.study.msgsys.dbservice;

import com.google.gson.Gson;

import java.util.Random;

public class DBCacheInformerImpl implements DBCacheInformer {
    private final Gson gson = new Gson();

    @Override
    public String getCacheInfo() {
        return gson.toJson(new CacheInfo());
    }

    private String attrVal(String name) {
        return String.valueOf(new Random().nextInt(2302));
    }

    private class CacheInfo{
        private String hit = attrVal("Hit");
        private String miss = attrVal("Miss");
        private String eviction = attrVal("Eviction");
        private String capacity = attrVal("Capacity");
        private String cacheSize = attrVal("CacheSize");
        private String timeToIdle = attrVal("TimeToIdleMs");
        private String timeToLive = attrVal("TimeToLiveMs");
    }
}
