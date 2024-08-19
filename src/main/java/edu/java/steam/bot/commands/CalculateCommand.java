package edu.java.steam.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.steam.bot.validator.IdValidator;
import org.springframework.stereotype.Component;

@Component
public class CalculateCommand implements Command {
    @Override
    public String command() {
        return "/calculate";
    }

    @Override
    public String description() {
        return "подсчитывает стоимость инвентаря (/calculate <steamid64>)";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String[] segments = text.split("\\s+");

        if (segments.length == 1) {
            return new SendMessage(chatId, "Нужно ввести команду вида /calculate <steamid64>");
        }

        if (!IdValidator.checkId(segments[1])) {
            return new SendMessage(chatId, "Некорректный SteamID");
        }

        return new SendMessage(chatId, "Выберите игру")
            .replyMarkup(new InlineKeyboardMarkup(
                new InlineKeyboardButton("Counter Strike 2").callbackData("csgo"),
                new InlineKeyboardButton("Dota 2").callbackData("dota"),
                new InlineKeyboardButton("Rust").callbackData("rust"))
            );
    }
}
