package edu.arapahoe.steamwebapi.Records;

// this record may seem redundant, but it is necessary for the JSON to be parsed correctly (kinda like an outer shell for the returned data) -- Claire
public record SteamUserStats(PlayerStats playerstats) {
}
