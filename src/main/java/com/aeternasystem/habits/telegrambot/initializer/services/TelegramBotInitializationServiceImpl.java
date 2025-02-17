package com.aeternasystem.habits.telegrambot.initializer.services;

import com.aeternasystem.habits.telegrambot.initializer.util.TelegramButtonFactory;
import com.aeternasystem.habits.web.client.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TelegramBotInitializationServiceImpl implements TelegramBotInitializationService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotInitializationServiceImpl.class);

    private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org";
    private static final String SET_WEBHOOK_ENDPOINT = "/bot%s/setWebhook?url=%s";
    private static final String SET_MENU_BUTTON_ENDPOINT = "/bot%s/setChatMenuButton";

    private final ApiClient apiClient;
    private final TelegramButtonFactory telegramButtonFactory;

    public TelegramBotInitializationServiceImpl(ApiClient apiClient, TelegramButtonFactory telegramButtonFactory) {
        this.apiClient = apiClient;
        this.telegramButtonFactory = telegramButtonFactory;
    }

    @Override
    public void setWebhook(String botToken, String webhookUrl) {
        String url = TELEGRAM_API_BASE_URL + String.format(SET_WEBHOOK_ENDPOINT, botToken, webhookUrl);
        apiClient.sendGetRequest(url, "Webhook set successfully", "Error setting webhook");
    }

    @Override
    public void setWebAppMenuButton(String webAppUrl, String botToken) {
        String url = TELEGRAM_API_BASE_URL + String.format(SET_MENU_BUTTON_ENDPOINT, botToken);
        Map<String, Object> menuButton = telegramButtonFactory.createWebAppMenuButton(webAppUrl);
        apiClient.sendPostRequest(url, Map.of("menu_button", menuButton),
                "Menu button set successfully", "Error setting menu button");
    }
}
