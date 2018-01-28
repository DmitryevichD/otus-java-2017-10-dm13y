package by.dm13y.study.config;

import by.dm13y.study.cache.CacheLRU;
import by.dm13y.study.services.CacheInfo;
import by.dm13y.study.services.CacheInfoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Configuration
public class AppContext {

    @Bean
    public CacheInfo cashInfoService(){
        return new CacheInfoImpl();
    }

    @Bean
    public MBeanServer mBeanServer(){
        return ManagementFactory.getPlatformMBeanServer();
    }

    @Bean
    public ObjectName cacheBeanObjectName() throws Exception{
        return new ObjectName(CacheLRU.CACHE_MXBEAN_OBJ_NAME);
    }

}
