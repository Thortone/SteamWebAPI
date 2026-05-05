package edu.arapahoe.steamwebapi;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.arapahoe.steamwebapi.Records.*;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class APIController {

    // a client service object and gameEntryRepository object are created here so their methods can be called -- Claire
    private final ClientService ClientService;
    private final GameEntryRepository gameEntryRepository;
    private final SteamGameService steamGameService;

    public APIController(ClientService clientService, GameEntryRepository gameEntryRepository, SteamGameService steamGameService) {
        this.ClientService = clientService;
        this.gameEntryRepository = gameEntryRepository;
        this.steamGameService = steamGameService;
    }

//    // takes in appId and steamId and returns that players stats/achievements for said game -- Claire
//    // this is not currently used, api key is not needed for this project at the moment -- Claire
//    @GetMapping("/stats/{appId}/{steamId}")
//    public SteamUserStats getPlayerStats(@PathVariable String appId, @PathVariable String steamId) {
//
//        return ClientService.getUserStats(appId, steamId, apiKey);
//    }

    // asking ai questions
    @PostMapping(path = "/ask/{appId}", produces = "application/json")
    public Answer ask(@RequestBody @Valid String question, @PathVariable int appId) throws JsonProcessingException {

        List<GameEntryInfo> gameEntries = gameEntryRepository.findByAppIdOrderByTimestampDesc(appId);
        String gameName = ClientService.getGameName(String.valueOf(appId));
        Question newQuestion = new Question(gameName, gameEntries, question);

        return steamGameService.askQuestion(newQuestion);
    }


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
    public List<GameEntryInfo> recordNow(@PathVariable int appid) throws JsonProcessingException {

        // game entry is created and saved to the database
        gameEntryRepository.save(ClientService.recordPlayerCount(appid, gameEntryRepository.count()));

        // still allows you to see the history over time
        return gameEntryRepository.findByAppIdOrderByTimestampDesc(appid);
    }

    // somewhat vestigial but this is useful if you just want the game name for a given app id
    @GetMapping("/gameName/{appId}")
    String getGameName(@PathVariable String appId) throws JsonProcessingException {
        return ClientService.getGameName(appId);
    }
}
