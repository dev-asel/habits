package com.aeternasystem.habits.telegrambot.util;

import com.aeternasystem.habits.telegrambot.enums.CommandName;

public class TelegramBotUtil {

    private TelegramBotUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static CommandName getCommand(String messageText) {
        String commandText = messageText.split(" " )[0];
        return fromCommand(commandText);
    }

    public static CommandName fromCommand(String commandText) {
        for (CommandName commandName : CommandName.values()) {
            if (commandName.getCommand().equals(commandText)) {
                return commandName;
            }
        }
        throw new IllegalArgumentException("Invalid command: " + commandText);
    }
}