package com.aeternasystem.habits.telegrambot.commands;

import com.aeternasystem.habits.telegrambot.util.BotMessageUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public abstract class BaseCommand implements Command {
    protected final BotMessageUtil botMessageUtil;

    protected BaseCommand(BotMessageUtil botMessageUtil) {
        this.botMessageUtil = botMessageUtil;
    }

    @Override
    public SendMessage execute(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String messageText = createMessage(update);
        return new SendMessage(chatId.toString(), messageText);
    }

    protected abstract String createMessage(Update update);
}