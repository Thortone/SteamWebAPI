package edu.arapahoe.steamwebapi.Records;

import edu.arapahoe.steamwebapi.GameEntryInfo;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record Question(
        @NotBlank(message = "Game Name is required") String gameName,
        @NotBlank(message = "Player Count Entries is required") List<GameEntryInfo> playerCountEntries,
        @NotBlank(message = "Question is required") String question) {
}
