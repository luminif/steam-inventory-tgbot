package edu.java.steam.scrapper.handler;

import edu.java.steam.scrapper.client.SteamClient;
import edu.java.steam.scrapper.client.SteamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SteamClientHandler {
    private final SteamClient steamClient;
    @Value("${steam.api-key}")
    private String apiKey;

    public String getResponse(long steamId, String game) {
        try {
            var text = new StringBuilder();
            List<SteamResponse> response = steamClient.fetchInventory(steamId, game, apiKey);
            log.info(response.toString());
            int countItems = 0;
            double price = 0;
            double priceReal = 0;

            HashMap<SteamResponse, Integer> items = new HashMap<>();

            for (var item : response) {
                SteamResponse currentItem = new SteamResponse(
                    item.itemName(),
                    item.price() == null ? 0 : item.price(),
                    item.priceReal() == null ? 0 : item.priceReal()
                );

                items.put(currentItem, items.getOrDefault(currentItem, 0) + 1);

                price += currentItem.price();
                priceReal += currentItem.priceReal();
                countItems += 1;
            }

            List<Map.Entry<SteamResponse, Integer>> sortedItems = items.entrySet().stream()
                .sorted(Map.Entry.<SteamResponse, Integer>comparingByKey(Comparator.comparingDouble(SteamResponse::price)).reversed())
                .toList();

            sortedItems.forEach(item -> {
                SteamResponse current = item.getKey();
                text.append("%s - %s\uD83D\uDCB2".formatted(current.itemName(), String.format("%.2f", current.price())))
                    .append(" (x%d)".formatted(item.getValue()))
                    .append("\n");
            });

            text.append("Всего %d вещей.".formatted(countItems))
                .append("\n")
                .append("Цена по стиму: %s\uD83D\uDCB0".formatted(String.format("%.2f", price)))
                .append("\n")
                .append("Реальная цена: %s\uD83D\uDCB0".formatted(priceReal == 0 ? "(?)" : String.format("%.2f", priceReal)))
                .append("\n");

            return text.toString();
        } catch (WebClientResponseException e) {
            return "Кажется, профиль или инвентарь скрыт";
        }
    }
}
