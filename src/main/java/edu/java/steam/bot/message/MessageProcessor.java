package edu.java.steam.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.steam.bot.bot.Bot;
import edu.java.steam.bot.commands.Command;
import edu.java.steam.scrapper.handler.SteamClientHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MessageProcessor implements UserMessageProcessor {
    private final List<Command> commands;
    private final SteamClientHandler clientHandler;
    private final Bot bot;
    private final List<Update> updates = new ArrayList<>();
    private static final int MAX_LENGTH = 4096;

    public MessageProcessor(
        List<Command> commands,
        SteamClientHandler clientHandler,
        @Lazy Bot bot
    ) {
        this.commands = commands;
        this.clientHandler = clientHandler;
        this.bot = bot;
    }

    @Override
    public List<Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = 0;

        if (update.callbackQuery() != null) {
            chatId = update.callbackQuery().message().chat().id();
            String game = update.callbackQuery().data();
            bot.execute(new DeleteMessage(chatId, update.callbackQuery().message().messageId()));

            long steamId = Long.parseLong(updates.getFirst().message().text().split("\\s+")[1]);
            updates.remove(0);
            log.info(String.valueOf(steamId));

            String response = clientHandler.getResponse(steamId, game);

            if (response.length() > MAX_LENGTH) {
                for (int i = 0; i < response.length(); i += MAX_LENGTH) {
                    bot.execute(new SendMessage(chatId, response.substring(i, Math.min(response.length(), i + MAX_LENGTH))));
                }
                return new SendMessage(chatId, "");
            } else {
                return new SendMessage(chatId, response);
            }

        } else if (update.message() != null) {
            updates.add(update);
            chatId = update.message().chat().id();

            for (var command : commands) {
                if (command.supports(update)) {
                    return command.handle(update);
                }
            }
        }

        return new SendMessage(chatId, "Данная команда не поддерживается");
    }
}
