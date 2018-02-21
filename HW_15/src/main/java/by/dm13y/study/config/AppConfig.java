package by.dm13y.study.config;

import by.dm13y.study.services.database.CacheInfoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CacheInfoImpl getCacheInfoImpl(){
        return new CacheInfoImpl();
    }
}
