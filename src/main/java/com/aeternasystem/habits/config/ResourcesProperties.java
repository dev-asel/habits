package com.aeternasystem.habits.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties
@Component
@Getter
@Setter
public class ResourcesProperties {
    private String telegramBotUsername;
    private String telegramBotToken;
    private String telegramBotWebhookUrl;
    private String telegramBotUrl;
    private String telegramAppUrl;
    private String telegramOAuthUrl;
    private String telegramApiUrl;

    private String appUrl;
    private String homepageUrl;
    private String miniAppUrl;

    private String adminName;
    private String adminUsername;
    private String adminPassword;

    private String testName;
    private String testUsername;
    private String testPassword;

    private String secretKey;
}
