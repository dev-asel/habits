package com.aeternasystem.habits.telegrambot.commands.impl;

import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.telegrambot.commands.BaseCommand;
import com.aeternasystem.habits.telegrambot.enums.BotMessageType;
import com.aeternasystem.habits.telegrambot.util.BotMessageUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Getter
@Setter
@Component
public class StartCommand extends BaseCommand {
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(UserService userService, BotMessageUtil botMessageUtil) {
        super(botMessageUtil);
        this.userService = userService;
    }

    @Override
    protected String createMessage(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String name = message.getFrom().getFirstName();

        userService.register(chatId.toString(), name);
        return botMessageUtil.getBotMessage(BotMessageType.START_MESSAGE);
    }
}