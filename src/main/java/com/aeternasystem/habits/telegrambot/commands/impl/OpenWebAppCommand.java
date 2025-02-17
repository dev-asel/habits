package com.aeternasystem.habits.telegrambot.commands.impl;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.telegrambot.button.TelegramButtonService;
import com.aeternasystem.habits.telegrambot.commands.AbstractButtonCommand;
import com.aeternasystem.habits.telegrambot.enums.BotMessageType;
import com.aeternasystem.habits.telegrambot.util.BotMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class OpenWebAppCommand extends AbstractButtonCommand {

    private static final Logger logger = LoggerFactory.getLogger(OpenWebAppCommand.class);

    private final String appUrl;

    public OpenWebAppCommand(ResourcesProperties resourcesProperties, BotMessageUtil botMessageUtil, TelegramButtonService telegramButtonService) {
        super(botMessageUtil, telegramButtonService);
        this.appUrl = resourcesProperties.getAppUrl();
    }

    @Override
    protected String createMessage(Update update) {
        return botMessageUtil.getBotMessage(BotMessageType.OPEN_WEB_MESSAGE);
    }

    @Override
    public InlineKeyboardButton createButton() {
        return createUrlButton(botMessageUtil.getBotMessage(BotMessageType.OPEN_WEB_BUTTON_MESSAGE), appUrl);
    }
}