package edu.java.steam.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.steam.bot.commands.Command;
import edu.java.steam.bot.configuration.ApplicationConfig;
import edu.java.steam.bot.message.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramBot {
    private final MessageProcessor messageProcessor;

    public Bot(ApplicationConfig config, MessageProcessor messageProcessor) {
        super(config.telegramToken());
        this.messageProcessor = messageProcessor;
        this.start();
    }

    private SetMyCommands createMenu() {
        List<Command> commands = messageProcessor.commands();
        List<BotCommand> botCommands = new ArrayList<>();
        commands.forEach(command -> botCommands.add(command.toApiCommand()));
        return new SetMyCommands(botCommands.toArray(new BotCommand[0]));
    }

    public SendMessage process(Update update) {
        return messageProcessor.process(update);
    }

    public void start() {
        execute(createMenu());
        setUpdatesListener(updates -> {
            updates.forEach(update -> execute(process(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
