package com.vaxtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VaccineReminderApplication {
    public static void main(String[] args) {
        SpringApplication.run(VaccineReminderApplication.class, args);
    }
}
