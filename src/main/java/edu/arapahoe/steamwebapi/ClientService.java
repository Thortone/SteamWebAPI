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

//    // sends a request to this link to get player stats -- Claire
//    // this is not currently used, api key is not needed for this project at the moment -- Claire
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
    // will return a SteamGameStats object that contains the number of players currently playing the game -- Claire
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

    // sends a request to this link -- Claire
    // takes in an appId and returns the game name -- Claire
    // https://store.steampowered.com/api/appdetails?appids=appId
    public String getGameName(String appId) {

        // this object mapper is used to parse the json response from the steam api -- Claire
        // gathering this result as a JSON node object is easier in this case because there is a gargantuan amount of data retrieved in this request -- Claire
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

    // when this method is called, it will use a provided appId and entryId to create a new GameEntry that is returned -- Claire
    public GameEntry recordPlayerCount(int appId, long entryId) {

        SteamGameStats currentGameStats = getGameStats(appId);
        String gameName = getGameName(String.valueOf(appId));

        GameEntry record = new GameEntry();

        // not the cleanest way to do this, but it works for now -- Claire
        record.setId((int) entryId + 1);

        record.setAppId(appId);
        record.setGameName(gameName);
        record.setPlayerCount(currentGameStats.response().player_count());
        record.setTimestamp(java.time.LocalDate.now());

        return record;
    }

}
