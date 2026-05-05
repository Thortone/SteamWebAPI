package edu.arapahoe.steamwebapi;
import edu.arapahoe.steamwebapi.Records.*;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SteamGameAIService implements SteamGameService {
    private final ChatClient chatClient;

    public SteamGameAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer askQuestion(Question question) {

        String prompt =
                "Answer this question about " + question.gameName() + "with this player count information:\n";

        if (question.playerCountEntries().size() > 5) {
            for (int i = 0; i < 5; i++) {
                GameEntryInfo currentEntry = question.playerCountEntries().get(i);
                prompt += currentEntry.getPlayerCount() + " players at " + currentEntry.getTimestamp() + "\n";
            }
        }
        else {
            for (GameEntryInfo entry : question.playerCountEntries()) {
                prompt += entry.getPlayerCount() + " players at " + entry.getTimestamp() + "\n";
            }
        }

        prompt += "\nQuestion: " + question.question();

        String answerText = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new Answer(question.gameName(), answerText);
    }

}
