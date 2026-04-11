package edu.arapahoe.steamwebapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameEntryRepository extends JpaRepository<GameEntry, Integer> {
    List<GameEntryInfo> findAllByOrderByAppIdAsc();
    List<GameEntryInfo> findByAppIdOrderByTimestampAsc(int appId);
}