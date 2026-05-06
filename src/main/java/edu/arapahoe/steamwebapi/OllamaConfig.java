package edu.arapahoe.steamwebapi;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class OllamaConfig {

    @Bean
    public OllamaApi ollamaApi() {

        //ensures the chat client doesn't go on forever, but also that it has enough time to respond
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMinutes(5));

        ReactorClientHttpRequestFactory requestFactory =
                new ReactorClientHttpRequestFactory(httpClient);

        // specifies the exact url for the ollama api
        RestClient.Builder restClientBuilder = RestClient.builder()
                .baseUrl("http://127.0.0.1:11434")
                .requestFactory(requestFactory);

        return OllamaApi.builder()
                .baseUrl("http://127.0.0.1:11434")
                .restClientBuilder(restClientBuilder)
                .build();
    }

}
