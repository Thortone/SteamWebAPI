package edu.arapahoe.steamwebapi;

import edu.arapahoe.steamwebapi.Records.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import edu.arapahoe.steamwebapi.entity.GamePlayerCount;
import edu.arapahoe.steamwebapi.repository.GamePlayerCountRepository;
import java.time.LocalDateTime;


@Service
public class ClientService {
    // the repository is used to save the player count -- Matt
    private final GamePlayerCountRepository repository;

    // the rest client is created so we can use it to make requests -- Claire
    private final RestClient restClient;
    // the constructor is used to inject the rest client and the repository -- Matt
    public ClientService(RestClient restClient, GamePlayerCountRepository repository) {
        this.restClient = restClient;
        this.repository = repository;
    }

    // sends a request to this link -- Claire
    // http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=appId&key=apiKey&steamid=steamId
    public SteamUserStats getUserStats(String appId, String steamId, String apiKey) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ISteamUserStats/GetUserStatsForGame/v0002/")
                        .queryParam("appid", appId)
                        .queryParam("key", apiKey)
                        .queryParam("steamid", steamId)
                        .build())
                .retrieve()
                .body(SteamUserStats.class);
    }


    // sends a request to this link -- Claire
    // http://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/?appid=appId
    public SteamGameStats getGameStats(String appId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ISteamUserStats/GetNumberOfCurrentPlayers/v1/")
                        .queryParam("appid", appId)
                        .build())
                .retrieve()
                .body(SteamGameStats.class);
    }
    // records the player count for a game -- Matt
    public void recordPlayerCount(String appId) {
        try {
            SteamGameStats stats = getGameStats(appId);

            if (stats == null || stats.response() == null) return;

            int count = stats.response().player_count();

            GamePlayerCount record = new GamePlayerCount();
            record.setAppId(Integer.parseInt(appId));
            record.setPlayerCount(count);
            record.setRecordedAt(LocalDateTime.now());

            repository.save(record);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
