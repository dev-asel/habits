package com.aeternasystem.habits.telegrambot.auth.services;

import com.aeternasystem.habits.util.crypto.enums.EncodingType;

import java.util.Map;

public interface TelegramAuthVerificationService {
    boolean verifyMiniAppTelegramAuth(Map<String, String> authParams);
    boolean verifyWidgetTelegramAuth(Map<String, String> authParams);
    boolean verifyTelegramAuth(Map<String, String> authParams, EncodingType encodingType, byte[] secretKey);
    byte[] calculateMiniAppSecretKey(String botToken);
    byte[] calculateWidgetSecretKey(String botToken);
}