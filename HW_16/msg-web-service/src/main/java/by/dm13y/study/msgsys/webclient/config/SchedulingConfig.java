package by.dm13y.study.msgsys.webclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PreDestroy;

@EnableScheduling
@Configuration
@ComponentScan("by.dm13y.study.msgsys.webclient.scheduler")
public class SchedulingConfig implements SchedulingConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);

    private final ThreadPoolTaskScheduler taskScheduler;
    public SchedulingConfig(){
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setErrorHandler(task -> logger.error("Exception in @Scheduled task", task));
        taskScheduler.initialize();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler);
    }

    @PreDestroy
    private void preDestroy(){
        taskScheduler.shutdown();
    }
}
