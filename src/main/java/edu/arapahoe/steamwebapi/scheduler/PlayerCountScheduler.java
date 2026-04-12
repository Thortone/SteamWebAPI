package edu.arapahoe.steamwebapi.Scheduler;

import edu.arapahoe.steamwebapi.ClientService;
import edu.arapahoe.steamwebapi.GameEntryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// this class is responsible for scheduling the player count tracking -- Matt
public class PlayerCountScheduler {
    // the client service is used to make requests to the steam api -- Matt
    private final ClientService clientService;
    // the list of tracked games (use game IDs)-- Matt

    //    730 = Counter-Strike: Global Offensive
    //    570 = Dota 2
    //    8930 = Civilization V
    //    413150 = Stardew Valley
    //    1424330 = Wobbledogs

    private final List<Integer> trackedGames = List.of(730, 570, 8930, 413150, 1424330);

    private final GameEntryRepository gameEntryRepository;

    // the constructor is used to inject the client service -- Matt
    public PlayerCountScheduler(ClientService clientService, GameEntryRepository gameEntryRepository) {
        this.clientService = clientService;
        this.gameEntryRepository = gameEntryRepository;
    }
    // runs every 5 minutes (adjustable)-- Matt
    @Scheduled(fixedRate = 300000)
    public void trackPlayerCounts() {
        for (Integer appId : trackedGames) {
            gameEntryRepository.save(clientService.recordPlayerCount(appId, gameEntryRepository.count()));
        }
    }
}
