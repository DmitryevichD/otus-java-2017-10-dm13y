package by.dm13y.study.msgsys.webclient.config;

import by.dm13y.study.msgsys.webclient.msg.MsgClient;
import by.dm13y.study.msgsys.webclient.websockets.CacheInfoWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@ComponentScan("by.dm13y.study.msgsys.webclient.websockets")
public class WebSocketConfig implements WebSocketConfigurer{

    @Autowired
    private CacheInfoWS cacheInfoWS;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(cacheInfoWS, "/ws/cacheinfo")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Bean
    public MsgClient msgClient(){
        return new MsgClient();
    }
}
