package com.aeternasystem.habits.telegrambot.auth.services.impl;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.telegrambot.auth.services.TelegramAuthVerificationService;
import com.aeternasystem.habits.telegrambot.auth.util.TelegramAuthUtil;
import com.aeternasystem.habits.util.crypto.CryptographyUtil;
import com.aeternasystem.habits.util.crypto.enums.EncodingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class TelegramAuthVerificationServiceImpl implements TelegramAuthVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramAuthVerificationServiceImpl.class);

    private static final String HMAC_SHA256 = "HmacSHA256";

    private final String botToken;

    public TelegramAuthVerificationServiceImpl(ResourcesProperties resourcesProperties) {
        this.botToken = resourcesProperties.getTelegramBotToken();
    }

    @Override
    public boolean verifyMiniAppTelegramAuth(Map<String, String> authParams) {
        byte[] secretKey = calculateMiniAppSecretKey(botToken);
        return verifyTelegramAuth(authParams, EncodingType.HEX, secretKey);
    }

    @Override
    public boolean verifyWidgetTelegramAuth(Map<String, String> authParams) {
        byte[] secretKey = calculateWidgetSecretKey(botToken);
        return verifyTelegramAuth(authParams, EncodingType.HEX, secretKey);
    }

    @Override
    public boolean verifyTelegramAuth(Map<String, String> authParams, EncodingType encodingType, byte[] secretKey) {
        String receivedHash = authParams.remove("hash" );
        String dataCheckString = TelegramAuthUtil.buildDataCheckString(authParams);

        try {
            String calculatedHash = CryptographyUtil.calculateHash(dataCheckString, secretKey, encodingType);
            return receivedHash.compareToIgnoreCase(calculatedHash) == 0;
        } catch (Exception e) {
            logger.error("Error validating auth data", e);
            return false;
        }
    }

    @Override
    public byte[] calculateMiniAppSecretKey(String botToken) {
        return CryptographyUtil.getHmacSha256Bytes(botToken, "WebAppData" );
    }

    @Override
    public byte[] calculateWidgetSecretKey(String botToken) {
        return CryptographyUtil.getSha256Bytes(botToken);
    }
}