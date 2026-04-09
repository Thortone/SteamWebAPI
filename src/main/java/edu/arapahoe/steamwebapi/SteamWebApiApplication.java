package edu.arapahoe.steamwebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//added scheduling annotation (enables scheduling)
@EnableScheduling
@SpringBootApplication
public class SteamWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteamWebApiApplication.class, args);
    }

}
