package com.dataint.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class AppMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMonitorApplication.class, args);
    }

}
