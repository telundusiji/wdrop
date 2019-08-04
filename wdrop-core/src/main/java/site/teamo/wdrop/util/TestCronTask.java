package site.teamo.wdrop.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class TestCronTask extends CronTaskUtil {

    public TestCronTask(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        super(threadPoolTaskScheduler);
    }

    @Override
    public String cronString() {
        return "0/1 * * * * ?";
    }

    @Override
    public void run() {
        System.out.println("111");
    }

    public static void main(String[] args){
        new TestCronTask(new ThreadPoolTaskScheduler()).start();
    }
}
