package edu.arapahoe.steamwebapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
// this class represents the player count for a specific game -- Matt
public class GamePlayerCount {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // this is the primary key for the table -- Matt
    private Long id;
    // this is the app id for the game -- Matt
    private Integer appId;
    private Integer playerCount;
    private LocalDateTime recordedAt;
    // getters -- Matt
    public Long getId()
    { return id; }
    public Integer getAppId()
    { return appId; }
    public Integer getPlayerCount()
    { return playerCount; }
    public LocalDateTime getRecordedAt()
    { return recordedAt; }
    // setters -- Matt
    public void setId(Long id)
    { this.id = id; }
    public void setAppId(Integer appId)
    { this.appId = appId; }
    public void setPlayerCount(Integer playerCount)
    { this.playerCount = playerCount; }
    public void setRecordedAt(LocalDateTime recordedAt)
    { this.recordedAt = recordedAt; }
}