package edu.java.steam.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.steam.bot.commands.Command;

import java.util.List;

public interface UserMessageProcessor {
    List<Command> commands();

    SendMessage process(Update update);
}
