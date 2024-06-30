package edu.java.steam.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "запускает бота";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "Бот для подсчета стоимости инвентаря Steam. "
                + "Чтобы узнать доступные команды, воспользуйтесь командой /help");
    }
}
