package wad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Chat2010Application {

    public static void main(String[] args) {
        SpringApplication.run(Chat2010Application.class, args);
    }
}
