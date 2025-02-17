package com.aeternasystem.habits.telegrambot.validation;

import com.aeternasystem.habits.exception.EmptyUpdateException;
import com.aeternasystem.habits.exception.InvalidCommandException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class TelegramBotValidator {

    public void validateUpdate(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            throw new EmptyUpdateException("Received update without message or text");
        }
    }

    public void validateCommand(String messageText) {
        if (!messageText.startsWith("/")) {
            throw new InvalidCommandException("Received command without text");
        }
    }
}