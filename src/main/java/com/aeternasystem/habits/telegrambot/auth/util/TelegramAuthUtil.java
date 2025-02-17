package com.aeternasystem.habits.telegrambot.auth.util;

import java.util.Map;
import java.util.stream.Collectors;

public class TelegramAuthUtil {

    private TelegramAuthUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String buildDataCheckString(Map<String, String> authData) {
        return authData.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n" ));
    }
}