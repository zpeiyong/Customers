package com.dataint.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServiceTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTopicApplication.class, args);
    }
}
