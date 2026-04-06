package edu.arapahoe.steamwebapi;

import edu.arapahoe.steamwebapi.Records.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

}
