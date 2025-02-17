package com.aeternasystem.habits.telegrambot.auth.util;

import com.aeternasystem.habits.exception.InvalidAuthParamsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;

public class TelegramParamsValidationUtil {

    private static final Logger logger = LoggerFactory.getLogger(TelegramParamsValidationUtil.class);
    private static final String AUTH_DATE_REGEX = "^\\d{10}$";

    private TelegramParamsValidationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void validateAuthParams(Map<String, String> authParams) {
        if (authParams == null || authParams.isEmpty()) {
            throw new InvalidAuthParamsException("Authentication parameters are missing");
        }

        validateAuthDate(authParams.get("auth_date"));
    }

    public static void validateAuthDate(String authDate) {
        if (authDate == null) {
            throw new IllegalArgumentException("Auth date is required.");
        }

        if (!authDate.matches(AUTH_DATE_REGEX)) {
            throw new IllegalArgumentException("Invalid authDate format. Must be a 10-digit Unix timestamp." );
        }

        long maxAgeInSeconds = 86400;
        if (!isAuthDateValid(Long.parseLong(authDate), maxAgeInSeconds)) {
            throw new IllegalArgumentException("Auth date is too old or invalid" );
        }
        logger.info("Auth date is valid");
    }

    public static boolean isAuthDateValid(long authDate, long maxAgeInSeconds) {
        long currentTime = Instant.now().getEpochSecond();
        return currentTime - authDate <= maxAgeInSeconds;
    }
}