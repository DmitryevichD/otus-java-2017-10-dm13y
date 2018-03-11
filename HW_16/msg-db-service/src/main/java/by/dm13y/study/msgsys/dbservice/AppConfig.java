package by.dm13y.study.msgsys.dbservice;

import by.dm13y.study.msgsys.api.SenderType;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public DBCacheInformer dbCacheInformer(){
        return new DBCacheInformerImpl();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean
    public DBMsgRecipient dbMsgRecipient(){
        return new DBMsgRecipient("", SenderType.DB_SERVICE);
    }
}
