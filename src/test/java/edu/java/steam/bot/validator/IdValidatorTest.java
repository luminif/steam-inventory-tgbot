package edu.java.steam.bot.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"78975978177965978", "78975990777965978"})
    void correctFormat(String id) {
        assertTrue(IdValidator.checkId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "789759781779659785"})
    void invalidFormat(String id) {
        assertFalse(IdValidator.checkId(id));
    }
}
