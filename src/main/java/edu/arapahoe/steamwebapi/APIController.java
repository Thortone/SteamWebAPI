package edu.arapahoe.steamwebapi;
import edu.arapahoe.steamwebapi.Records.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/api")
public class APIController {

    // a client service object and gameEntryRepository object are created here so their methods can be called -- Claire
    private final ClientService ClientService;
    private final GameEntryRepository gameEntryRepository;

    public APIController(ClientService clientService, GameEntryRepository gameEntryRepository) {
        this.ClientService = clientService;
        this.gameEntryRepository = gameEntryRepository;
    }

//    // takes in appId and steamId and returns that players stats/achievements for said game -- Claire
//    // this is not currently used, api key is not needed for this project at the moment -- Claire
//    @GetMapping("/stats/{appId}/{steamId}")
//    public SteamUserStats getPlayerStats(@PathVariable String appId, @PathVariable String steamId) {
//
//        return ClientService.getUserStats(appId, steamId, apiKey);
//    }


    // takes in appId and returns a gamestats object containing the number of players currently playing that game
    @GetMapping("/gamestats/{appId}")
    public SteamGameStats getGameStats(@PathVariable int appId) {
        return ClientService.getGameStats(appId);
    }

    // takes in appId as a path variable and returns a list of GameEntryInfo objects in the table to show player count over time
    @GetMapping("/games/{appid}/history")
    List<GameEntryInfo> getGameHistory(@PathVariable int appid) {
        return gameEntryRepository.findByAppIdOrderByTimestampDesc(appid);
    }

    // does the same thing as getGameHistory, but also records the current player count for the game
    @GetMapping("/games/{appid}/record")
    public List<GameEntryInfo> recordNow(@PathVariable int appid) {

        // game entry is created and saved to the database
        gameEntryRepository.save(ClientService.recordPlayerCount(appid, gameEntryRepository.count()));

        // still allows you to see the history over time
        return gameEntryRepository.findByAppIdOrderByTimestampDesc(appid);
    }

    // somewhat vestigial but this is useful if you just want the game name for a given app id
    @GetMapping("/gameName/{appId}")
    String getGameName(@PathVariable String appId) {
        return ClientService.getGameName(appId);
    }
}
