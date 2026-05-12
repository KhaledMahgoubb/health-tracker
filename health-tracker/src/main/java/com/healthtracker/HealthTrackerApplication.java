package com.healthtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main class fe package el ab: com.healthtracker
// Keda Spring hayeshof auth, meal, dailylog, progress automatically
@SpringBootApplication
public class HealthTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthTrackerApplication.class, args);
    }
}
