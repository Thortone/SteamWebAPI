package edu.arapahoe.steamwebapi;

import java.time.LocalDate;

/**
 * Projection for {@link GameEntry}
 */
public interface GameEntryInfo {
    Integer getId();

    Integer getAppId();

    String getGameName();

    Integer getPlayerCount();

    LocalDate getTimestamp();
}