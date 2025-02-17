package com.aeternasystem.habits.telegrambot.commands;

import com.aeternasystem.habits.telegrambot.button.TelegramButtonService;
import com.aeternasystem.habits.telegrambot.util.BotMessageUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public abstract class AbstractButtonCommand extends BaseCommand {

    private final TelegramButtonService telegramButtonService;

    protected AbstractButtonCommand(BotMessageUtil botMessageUtil, TelegramButtonService telegramButtonService) {
        super(botMessageUtil);
        this.telegramButtonService = telegramButtonService;
    }

    @Override
    public SendMessage execute(Update update) {
        SendMessage message = super.execute(update);
        telegramButtonService.setReplyMarkup(message, createButton());
        return message;
    }

    protected InlineKeyboardButton createMiniAppButton(String text, String webAppUrl) {
        return telegramButtonService.createButton(text, null, webAppUrl);
    }

    protected InlineKeyboardButton createUrlButton(String text, String url) {
        return telegramButtonService.createButton(text, url, null);
    }

    protected abstract InlineKeyboardButton createButton();
}