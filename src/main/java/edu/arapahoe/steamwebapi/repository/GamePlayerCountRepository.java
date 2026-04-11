package edu.arapahoe.steamwebapi.repository;

import edu.arapahoe.steamwebapi.entity.GamePlayerCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamePlayerCountRepository
        extends JpaRepository<GamePlayerCount, Long> {

    List<GamePlayerCount> findByAppIdOrderByRecordedAtAsc(Integer appId);
}