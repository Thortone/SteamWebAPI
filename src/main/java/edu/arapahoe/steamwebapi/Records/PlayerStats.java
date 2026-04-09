package edu.arapahoe.steamwebapi.Records;

import java.util.List;

// every player's stats have a steamID, gameName, and a list of stats and achievements -- Claire
public record PlayerStats(
        String steamID,
        String gameName,
        List<StatEntry> stats,
        List<AchievementEntry> achievements
) {

}
