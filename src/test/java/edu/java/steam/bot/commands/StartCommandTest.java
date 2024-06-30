package edu.java.steam.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest {
    @InjectMocks
    private StartCommand startCommand;

    @Test
    void command() {
        String actual = startCommand.command();
        assertEquals("/start", actual);
    }

    @Test
    void description() {
        String actual = startCommand.description();
        assertEquals("запускает бота", actual);
    }
}
