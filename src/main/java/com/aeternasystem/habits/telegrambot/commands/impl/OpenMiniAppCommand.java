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

import static com.aeternasystem.habits.telegrambot.enums.BotMessageType.OPEN_APP_BUTTON_MESSAGE;


@Component
public class OpenMiniAppCommand extends AbstractButtonCommand {

    private static final Logger logger = LoggerFactory.getLogger(OpenMiniAppCommand.class);

    private final String miniAppUrl;

    public OpenMiniAppCommand(ResourcesProperties resourcesProperties, BotMessageUtil botMessageUtil, TelegramButtonService telegramButtonService) {
        super(botMessageUtil, telegramButtonService);
        this.miniAppUrl = resourcesProperties.getMiniAppUrl();
    }

    @Override
    protected String createMessage(Update update) {
        return botMessageUtil.getBotMessage(BotMessageType.OPEN_APP_MESSAGE);
    }

    @Override
    protected InlineKeyboardButton createButton() {
        return createMiniAppButton(botMessageUtil.getBotMessage(OPEN_APP_BUTTON_MESSAGE), miniAppUrl);
    }
}