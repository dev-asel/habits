package com.aeternasystem.habits.telegrambot.button;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;

@Service
public class TelegramButtonServiceImpl implements TelegramButtonService {

    @Override
    public InlineKeyboardButton createButton(String text, String url, String webAppUrl) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);

        if (url != null) {
            button.setUrl(url);
        }

        if (webAppUrl != null) {
            WebAppInfo webAppInfo = new WebAppInfo();
            webAppInfo.setUrl(webAppUrl);
            button.setWebApp(webAppInfo);
        }

        return button;
    }

    @Override
    public InlineKeyboardMarkup createKeyboardMarkup(InlineKeyboardButton button) {
        return new InlineKeyboardMarkup(List.of(List.of(button)));
    }

    @Override
    public void setReplyMarkup(SendMessage message, InlineKeyboardButton button) {
        message.setReplyMarkup(createKeyboardMarkup(button));
    }
}
