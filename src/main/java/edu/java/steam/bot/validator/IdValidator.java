package edu.java.steam.bot.validator;

public class IdValidator {
    public static boolean checkId(String id) {
        return id.matches("[0-9]{17}");
    }
}
