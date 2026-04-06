package edu.arapahoe.steamwebapi.Records;

// each achievement entry has a name and whether or not it was achieved (only 1, this does not return non-achieved achievements) -- Claire
public record AchievementEntry(String name, Integer achieved) {}
