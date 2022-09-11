package com.flz.rm.sb.pullconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.flz.rm.sb")
public class PullConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PullConsumerApplication.class, args);
    }
}
