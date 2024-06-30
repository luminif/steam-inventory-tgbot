package edu.java.steam.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculateCommandTest {
    @InjectMocks
    private CalculateCommand calculateCommand;

    @Test
    void command() {
        String actual = calculateCommand.command();
        assertEquals("/calculate", actual);
    }

    @Test
    void description() {
        String actual = calculateCommand.description();
        assertEquals("подсчитывает стоимость инвентаря (/calculate <steamid64>)", actual);
    }
}
