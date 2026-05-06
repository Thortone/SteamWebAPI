package edu.arapahoe.steamwebapi;
import edu.arapahoe.steamwebapi.Records.*;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SteamGameAIService implements SteamGameService {

    private final ChatClient chatClient;

    public SteamGameAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer askQuestion(Question question) {

        // Builds the prompt for the AI to answer the question

        String prompt =
                "Briefly Answer this question about " + question.gameName() + " with this player count information:\n";

        // Grab the top 5 player count entries
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

        // add the question to the prompt
        prompt += "\nQuestion: " + question.question();

        // Prompt looks something like this:
//        Briefly Answer this question about "Counter-Strike 2" with this player count information:
//        1320402 players at 2026-05-06
//        1171978 players at 2026-05-05
//        1189140 players at 2026-05-05
//        1189140 players at 2026-05-05
//        1189140 players at 2026-05-05
//
//        Question: {
//            "question": "What can you tell me about this game?"
//        }


        System.out.println(prompt);

        // sends the prompt to the AI and returns the answer
        String answerText = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        System.out.println("Recieved answer:");
        System.out.println(answerText);

        return new Answer(question.gameName(), answerText);
    }
}
