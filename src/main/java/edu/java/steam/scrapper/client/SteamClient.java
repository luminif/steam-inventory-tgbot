package edu.java.steam.scrapper.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SteamClient {
    private final WebClient webClient;
    @Value("${steam.base-url}")
    private String baseUrl;

    public SteamClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public SteamClient(String baseUrl) {
        if (!baseUrl.isEmpty()) {
            this.baseUrl = baseUrl;
        }

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public List<SteamResponse> fetchInventory(Long steamId, String game, String apiKey) {
        return webClient
            .get()
            .uri("/inventory?steam_id={steamId}&game={game}&language=russian&parse=str&key={apiKey}", steamId, game, apiKey)
            .retrieve()
            .bodyToFlux(SteamResponse.class)
            .collectList()
            .block();
    }
}
