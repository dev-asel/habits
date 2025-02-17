package com.aeternasystem.habits.telegrambot.auth.services;

import jakarta.servlet.http.HttpServletResponse;

public interface TelegramAuthService {
    void authenticateUserAndSetToken(HttpServletResponse response, String chatId, String name);
}