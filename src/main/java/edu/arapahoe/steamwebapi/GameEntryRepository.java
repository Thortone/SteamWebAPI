package edu.arapahoe.steamwebapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameEntryRepository extends JpaRepository<GameEntry, Integer> {

    // Selects a list of game entries based on the appId
    // SELECT * FROM game_entries WHERE app_id = appId ORDER BY timestamp DESC
    List<GameEntryInfo> findByAppIdOrderByTimestampDesc(int appId);
}