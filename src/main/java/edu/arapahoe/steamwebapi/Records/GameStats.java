package edu.arapahoe.steamwebapi.Records;

// Each game stat has a player count and a result (usually 1, kinda unnecessary but still there) -- Claire
public record GameStats(Integer player_count, Integer result) {
}
