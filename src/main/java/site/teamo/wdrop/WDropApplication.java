package site.teamo.wdrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "site.teamo.wdrop.*")
@ComponentScan("site.teamo.wdrop.*")
public class WDropApplication {
    public static void main(String[] args){
        SpringApplication.run(WDropApplication.class,args);
    }
}
