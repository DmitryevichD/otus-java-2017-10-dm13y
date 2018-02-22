package by.dm13y.study.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("by.dm13y.study.services")
public class AppConfig {
    @Bean
    public ContextAware contextAware(){
        return new ContextAware();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

}
