package edu.arapahoe.steamwebapi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "game_entries")
public class GameEntry {
    @Id
    @Column(name = "entry_id", nullable = false)
    private Integer entryId;

    @NotNull
    @Column(name = "app_id", nullable = false)
    private Integer appId;

    @Size(max = 100)
    @NotNull
    @Column(name = "game_name", nullable = false, length = 100)
    private String gameName;

    @NotNull
    @Column(name = "player_count", nullable = false)
    private Integer playerCount;

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    private LocalDate timestamp;

    public Integer getId() {
        return entryId;
    }

    public void setId(Integer id) {
        this.entryId = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

}