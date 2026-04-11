package edu.arapahoe.steamwebapi;

import edu.arapahoe.steamwebapi.Records.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class ClientService {

    // the rest client is created so we can use it to make requests -- Claire
    private final RestClient restClient;

    public ClientService(RestClient restClient) {
        this.restClient = restClient;
    }

//    // sends a request to this link -- Claire
//    // http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=appId&key=apiKey&steamid=steamId
//    public SteamUserStats getUserStats(String appId, String steamId, String apiKey) {
//        return restClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/ISteamUserStats/GetUserStatsForGame/v0002/")
//                        .queryParam("appid", appId)
//                        .queryParam("key", apiKey)
//                        .queryParam("steamid", steamId)
//                        .build())
//                .retrieve()
//                .body(SteamUserStats.class);
//    }

    // sends a request to this link -- Claire
    // http://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/?appid=appId
    public SteamGameStats getGameStats(int appId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ISteamUserStats/GetNumberOfCurrentPlayers/v1/")
                        .queryParam("appid", appId)
                        .build())
                .retrieve()
                .body(SteamGameStats.class);
    }

    public String getGameName(String appId) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(
                restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("store.steampowered.com")
                        .path("/api/appdetails")
                        .queryParam("appids", appId)
                        .build())
                .retrieve()
                .body(String.class));

        return root.get(appId).get("data").get("name").asString();
    }

    public GameEntry recordPlayerCount(int appId, long entryId) {
        SteamGameStats currentGameStats = getGameStats(appId);
        String gameName = getGameName(String.valueOf(appId));

        GameEntry record = new GameEntry();
        record.setId((int) entryId + 1);
        record.setAppId(appId);
        record.setGameName(gameName);
        record.setPlayerCount(currentGameStats.response().player_count());
        record.setTimestamp(java.time.LocalDate.now());

        return record;
    }

}
