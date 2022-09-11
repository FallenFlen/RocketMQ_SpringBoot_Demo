package com.flz.rm.sb.dead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.flz")
public class DeadMessageQueueApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeadMessageQueueApplication.class, args);
    }
}
