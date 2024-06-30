package edu.java.steam.scrapper.configuration;

import edu.java.steam.scrapper.client.SteamClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Value("${steam.base-url}")
    private String steamBaseUrl;

    @Bean
    public SteamClient steamClient() {
        return new SteamClient(steamBaseUrl);
    }
}
