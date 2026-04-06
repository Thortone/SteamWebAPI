package edu.arapahoe.steamwebapi;
import edu.arapahoe.steamwebapi.Records.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@RestController
@RequestMapping("/api")
public class APIController {

    // a client service object is created here so its methods can be called -- Claire
    private final ClientService ClientService;

    public APIController(ClientService clientService) {
        this.ClientService = clientService;
    }

    // takes in appId and steamId and returns that players stats/achievements for said game -- Claire
    @GetMapping("/stats/{appId}/{steamId}")
    public SteamUserStats getPlayerStats(@PathVariable String appId, @PathVariable String steamId) {

        // reads the API key from a file on my desktop -- Claire (really hoping to put this into a .env file)
        String apiKey = "";
        File apiKeyFile = new File("/Users/clairekayton/Desktop/SteamAPIKey/APIKey.txt");

        try (Scanner reader = new Scanner(apiKeyFile)) {
            while (reader.hasNextLine()) {
                apiKey = reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return ClientService.getUserStats(appId, steamId, apiKey);
    }

    // takes in appId and returns the number of players currently playing the game -- Claire
    @GetMapping("/gamestats/{appId}")
    public SteamGameStats getGameStats(@PathVariable String appId) {
        return ClientService.getGameStats(appId);
    }
}
