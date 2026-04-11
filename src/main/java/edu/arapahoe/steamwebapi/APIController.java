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

    // a client service object is created here so its methods can be called -- Claire
    private final ClientService ClientService;
    private final GameEntryRepository gameEntryRepository;

    public APIController(ClientService clientService, GameEntryRepository gameEntryRepository) {
        this.ClientService = clientService;
        this.gameEntryRepository = gameEntryRepository;
    }

//    // takes in appId and steamId and returns that players stats/achievements for said game -- Claire
//    @GetMapping("/stats/{appId}/{steamId}")
//    public SteamUserStats getPlayerStats(@PathVariable String appId, @PathVariable String steamId) {
//
//        return ClientService.getUserStats(appId, steamId, apiKey);
//    }


    // takes in appId and returns the number of players currently playing the game -- Claire
    @GetMapping("/gamestats/{appId}")
    public SteamGameStats getGameStats(@PathVariable int appId) {
        return ClientService.getGameStats(appId);
    }

    @GetMapping("/games/{appid}/history")
    List<GameEntryInfo> getGameHistory(@PathVariable int appid) {
        return gameEntryRepository.findByAppIdOrderByTimestampAsc(appid);
    }

    @GetMapping("/games/{appid}/record")
    public List<GameEntryInfo> recordNow(@PathVariable int appid) {

        gameEntryRepository.save(ClientService.recordPlayerCount(appid, gameEntryRepository.count()));

        return gameEntryRepository.findByAppIdOrderByTimestampAsc(appid);
    }

    @GetMapping("/gameName/{appId}")
    String getGameName(@PathVariable String appId) {
        return ClientService.getGameName(appId);
    }
}
