package site.teamo.wdrop.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

public abstract class CronTaskUtil implements Runnable{
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture<?> task;

    public CronTaskUtil(ThreadPoolTaskScheduler threadPoolTaskScheduler){
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }
    public abstract String cronString();

    public void start() {
        task = threadPoolTaskScheduler.schedule(this,new CronTrigger(cronString()));
    }

    public void stop() {
        if (task != null) {
            task.cancel(true);
        }
    }

    public void refresh() {
        stop();
        start();
    }
}
