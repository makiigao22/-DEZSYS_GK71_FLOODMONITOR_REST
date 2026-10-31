package com.example.floodmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@org.springframework.scheduling.annotation.EnableScheduling
@SpringBootApplication
public class FloodMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FloodMonitorApplication.class, args);
    }
}
