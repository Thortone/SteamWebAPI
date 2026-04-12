package edu.arapahoe.steamwebapi;

import org.springframework.boot.SpringApplication;

// allows scheduling of tasks -- Matt
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SteamWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteamWebApiApplication.class, args);
    }

}
