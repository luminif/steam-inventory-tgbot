package edu.java.steam.scrapper.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SteamResponse(
    @JsonProperty("marketname")
    String itemName,
    @JsonProperty("pricelatest")
    Double price,
    @JsonProperty("pricereal")
    Double priceReal
) {
}
