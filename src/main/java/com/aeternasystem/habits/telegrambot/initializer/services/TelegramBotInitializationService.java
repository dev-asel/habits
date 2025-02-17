package com.aeternasystem.habits.telegrambot.initializer.services;

public interface TelegramBotInitializationService {
    void setWebhook(String botToken, String webhookUrl);
    void setWebAppMenuButton(String webAppUrl, String botToken);
}