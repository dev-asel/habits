package com.aeternasystem.habits.telegrambot.auth.util;

import com.aeternasystem.habits.util.web.JsonUtil;
import com.aeternasystem.habits.util.web.QueryParamParser;

import java.util.Map;

public class TelegramParamsUtil {

    private TelegramParamsUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Map<String, String> getAuthParams(String initData) {
        return QueryParamParser.toMap(initData);
    }

    public static Map<String, String> getUserParams(String userString) {
        return JsonUtil.jsonToStringMap(userString);
    }
}