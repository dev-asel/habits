package com.aeternasystem.habits.telegrambot.auth.services.impl;

import com.aeternasystem.habits.exception.TelegramAuthException;
import com.aeternasystem.habits.telegrambot.auth.services.TelegramAuthVerificationService;
import com.aeternasystem.habits.telegrambot.auth.util.TelegramParamsValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TelegramAuthValidationService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramAuthValidationService.class);

    private final TelegramAuthVerificationService telegramAuthVerificationService;

    public TelegramAuthValidationService(TelegramAuthVerificationService telegramAuthVerificationService) {
        this.telegramAuthVerificationService = telegramAuthVerificationService;
    }

    public void validateMiniAppAuth(Map<String, String> authParams) {
        TelegramParamsValidationUtil.validateAuthParams(authParams);
        if (!telegramAuthVerificationService.verifyMiniAppTelegramAuth(authParams)) {
            throw new TelegramAuthException("Invalid Telegram authentication parameters");
        } else {
            logger.info("Telegram MiniApp Auth validation succeeded");
        }
    }

    public void validateWidgetAuth(Map<String, String> authParams) {
        TelegramParamsValidationUtil.validateAuthParams(authParams);
        if (!telegramAuthVerificationService.verifyWidgetTelegramAuth(authParams)) {
            throw new TelegramAuthException("Invalid Telegram authentication parameters");
        } else {
            logger.info("Telegram Widget Auth validation succeeded");
        }
    }
}