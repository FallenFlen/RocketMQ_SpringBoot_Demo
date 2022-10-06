package com.flz.rm.repeatedconsume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.flz")
public class RepeatedConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepeatedConsumeApplication.class, args);
    }
}
