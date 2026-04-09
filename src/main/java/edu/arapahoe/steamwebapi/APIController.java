package edu.arapahoe.steamwebapi;
import edu.arapahoe.steamwebapi.Records.*;
import org.springframework.web.bind.annotation.*;
import edu.arapahoe.steamwebapi.entity.GamePlayerCount;
import edu.arapahoe.steamwebapi.repository.GamePlayerCountRepository;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;



@RestController
@RequestMapping("/api")
public class APIController {
    // the repository is used to save the player count -- Matt
    private final GamePlayerCountRepository repository;

    @Value("${steam.api-key}")
    // the api key is stored in a .env file -- Matt
    private String apiKey;

    // a client service object is created here so its methods can be called -- Claire
    private final ClientService ClientService;

    // the constructor is used to inject the client service and the repository -- Matt
    public APIController(ClientService clientService,GamePlayerCountRepository repository) {
        this.ClientService = clientService;
        this.repository = repository;
    }

    @GetMapping("/stats/{appId}/{steamId}")
    // takes in appId and steamId and returns the player stats for that game -- Matt
        public SteamUserStats getPlayerStats(@PathVariable String appId, @PathVariable String steamId) {
            return ClientService.getUserStats(appId, steamId, apiKey);
        }

    // takes in appId and returns the number of players currently playing the game -- Claire
    @GetMapping("/gamestats/{appId}")
    public SteamGameStats getGameStats(@PathVariable String appId) {

        return ClientService.getGameStats(appId);
    }
    @GetMapping("/games/{appId}/history")
    // returns a list of all recorded player counts for a game -- Matt
    public List<GamePlayerCount> getHistory(@PathVariable int appId) {
        return repository.findByAppIdOrderByRecordedAtAsc(appId);
    }
    @PostMapping("/games/{appId}/record")
    // records the player count for a game -- Matt
    public String recordNow(@PathVariable String appId) {
        ClientService.recordPlayerCount(appId);
        return "Recorded player count for appId: " + appId;
    }
}
