package com.aeternasystem.habits.telegrambot.initializer.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramButtonFactory {
    public Map<String, Object> createWebAppMenuButton(String webAppUrl) {
        Map<String, Object> menuButton = new HashMap<>();
        menuButton.put("type", "web_app");
        menuButton.put("text", "Open App");
        menuButton.put("web_app", Map.of("url", webAppUrl));
        return menuButton;
    }
}