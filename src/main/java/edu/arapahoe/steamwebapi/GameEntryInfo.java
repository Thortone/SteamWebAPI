package edu.arapahoe.steamwebapi;

import java.time.LocalDate;

/**
 * Projection for {@link GameEntry}
 */


// getters for the info we want to display -- Claire

public interface GameEntryInfo {
    Integer getId();

    Integer getAppId();

    String getGameName();

    Integer getPlayerCount();

    LocalDate getTimestamp();
}