package by.dm13y.study.msgsys.webclient.scheduler;

import by.dm13y.study.msgsys.webclient.websockets.CacheInfoWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private CacheInfoWS cacheInfoWS;

    @Autowired
    public void setCacheInfoWS(CacheInfoWS cacheInfoWS) {
        this.cacheInfoWS = cacheInfoWS;
    }

    @Scheduled(fixedRate = 100)
    public void checkInputMsg(){
        cacheInfoWS.checkInputMsg();
    }
}
