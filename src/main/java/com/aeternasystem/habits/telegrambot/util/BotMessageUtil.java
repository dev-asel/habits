package com.aeternasystem.habits.telegrambot.util;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.telegrambot.enums.BotMessageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@Getter
@Setter
@Component
public class BotMessageUtil {

    private final String telegramAppUrl;
    private final String appUrl;
    private final ResourceBundle messages;

    public BotMessageUtil(ResourcesProperties resourcesProperties) {
        this.telegramAppUrl = resourcesProperties.getTelegramAppUrl();
        this.appUrl = resourcesProperties.getAppUrl();
        this.messages = ResourceBundle.getBundle("messages" );
    }

    public String getBotMessage(BotMessageType type) {
        return switch (type) {
            case BOT_INFO -> getFormattedMessage("bot.info", telegramAppUrl, appUrl);
            case START_MESSAGE -> getBotMessage("bot.start" );
            case OPEN_APP_MESSAGE -> getBotMessage("bot.open.app" );
            case OPEN_WEB_MESSAGE -> getBotMessage("bot.open.web" );
            case OPEN_APP_BUTTON_MESSAGE -> getBotMessage("bot.open.app.button" );
            case OPEN_WEB_BUTTON_MESSAGE -> getBotMessage("bot.open.web.button" );
            default -> throw new IllegalArgumentException(
                    getFormattedMessage("error.unknown", type));
        };
    }

    private String getBotMessage(String key) {
        return messages.getString(key);
    }

    private String getFormattedMessage(String key, Object... args) {
        String template = getBotMessage(key);
        return MessageFormat.format(template, args);
    }
}