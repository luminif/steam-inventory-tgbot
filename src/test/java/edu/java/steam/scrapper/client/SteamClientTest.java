package edu.java.steam.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteamClientTest {
    private WireMockServer wireMockServer;
    private SteamClient steamClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        String baseUrl = "http://localhost:" + wireMockServer.port();
        steamClient = new SteamClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void equalBodyTest() {
        Long steamId = 788890292929L;
        String game = "csgo";
        String apiKey = "576XJIS0J";

        String body =
            """
            [
                {
                    "marketname": "Tec-9",
                    "pricelatest": 4.75,
                    "pricereal": 3.95
                }
            ]
            """;

        stubFor(get(urlEqualTo("/inventory?steam_id=%s&game=%s&language=russian&parse=str&key=%s"
            .formatted(steamId.toString(), game, apiKey)))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)
            )
        );

        List<SteamResponse> steamResponse = steamClient.fetchInventory(steamId, game, apiKey);

        assertEquals("Tec-9", steamResponse.getFirst().itemName());
        assertEquals(4.75, steamResponse.getFirst().price());
        assertEquals(3.95, steamResponse.getFirst().priceReal());
    }
}
