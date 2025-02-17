package com.aeternasystem.habits.telegrambot.commands.impl;

import com.aeternasystem.habits.telegrambot.commands.BaseCommand;
import com.aeternasystem.habits.telegrambot.enums.BotMessageType;
import com.aeternasystem.habits.telegrambot.util.BotMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InfoCommand extends BaseCommand {

    private static final Logger logger = LoggerFactory.getLogger(InfoCommand.class);

    public InfoCommand(BotMessageUtil botMessageUtil) {
        super(botMessageUtil);
    }

    @Override
    protected String createMessage(Update update) {
        return botMessageUtil.getBotMessage(BotMessageType.BOT_INFO);
    }
}