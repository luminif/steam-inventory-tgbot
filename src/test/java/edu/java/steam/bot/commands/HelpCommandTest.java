package edu.java.steam.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {
    @Mock
    private List<Command> commands;
    @InjectMocks
    private HelpCommand helpCommand;

    @Test
    void command() {
        String actual = helpCommand.command();
        assertEquals("/help", actual);
    }

    @Test
    void description() {
        String actual = helpCommand.description();
        assertEquals("показывает список доступных команд", actual);
    }
}
