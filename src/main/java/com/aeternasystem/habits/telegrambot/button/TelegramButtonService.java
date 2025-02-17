package com.aeternasystem.habits.telegrambot.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface TelegramButtonService {
    InlineKeyboardButton createButton(String text, String url, String webAppUrl);
    InlineKeyboardMarkup createKeyboardMarkup(InlineKeyboardButton button);
    void setReplyMarkup(SendMessage message, InlineKeyboardButton button);
}