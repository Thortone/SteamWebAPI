
package edu.arapahoe.steamwebapi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    // specifies the base url for the rest client -- Claire
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://api.steampowered.com")
                .build();
    }

}
