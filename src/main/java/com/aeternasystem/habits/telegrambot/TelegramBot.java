package com.aeternasystem.habits.telegrambot;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.exception.MessageExecutionException;
import com.aeternasystem.habits.telegrambot.commands.Command;
import com.aeternasystem.habits.telegrambot.commands.impl.CommandFactory;
import com.aeternasystem.habits.telegrambot.initializer.services.TelegramBotInitializationService;
import com.aeternasystem.habits.telegrambot.util.TelegramBotUtil;
import com.aeternasystem.habits.telegrambot.validation.TelegramBotValidator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@Setter
@Component
public class TelegramBot extends TelegramWebhookBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private final CommandFactory commandFactory;

    private final String telegramBotToken;
    private final String telegramBotUsername;
    private final String telegramBotWebhookUrl;
    private final String appUrl;
    private final String miniAppUrl;
    private final TelegramBotValidator validator;
    private final TelegramBotInitializationService telegramBotInitializationService;

    public TelegramBot(CommandFactory commandFactory, ResourcesProperties resourcesProperties, TelegramBotValidator validator, TelegramBotInitializationService telegramBotInitializationService) {
        this.commandFactory = commandFactory;
        this.telegramBotToken = resourcesProperties.getTelegramBotToken();
        this.telegramBotUsername = resourcesProperties.getTelegramBotUsername();
        this.telegramBotWebhookUrl = resourcesProperties.getTelegramBotWebhookUrl();
        this.appUrl = resourcesProperties.getAppUrl();
        this.miniAppUrl = resourcesProperties.getMiniAppUrl();
        this.validator = validator;
        this.telegramBotInitializationService = telegramBotInitializationService;
    }

    @Override
    public String getBotUsername() {
        return telegramBotUsername;
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }

    @Override
    public String getBotPath() {
        return telegramBotWebhookUrl;
    }

    @PostConstruct
    public void init() {
        telegramBotInitializationService.setWebhook(telegramBotToken, telegramBotWebhookUrl);
        telegramBotInitializationService.setWebAppMenuButton(miniAppUrl, telegramBotToken);
    }

    @Override
    public SendMessage onWebhookUpdateReceived(Update update) {
        logger.info("Received webhook update: {}", update);
        try {
            validator.validateUpdate(update);
            return handleUpdate(update);
        } catch (Exception e) {
            logger.error("Error handling update: {}", e.getMessage(), e);
            return sendErrorMessage(update.getMessage().getChatId().toString(), e.getMessage());
        }
    }

    public SendMessage handleUpdate(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();
        Long chatId = message.getChatId();

        try {
            validator.validateCommand(messageText);
            return processCommand(messageText, update);
        } catch (Exception e) {
            return sendErrorMessage(chatId.toString(), e.getMessage());
        }
    }

    private SendMessage processCommand(String messageText, Update update) {
        Command command = commandFactory.createCommand(TelegramBotUtil.getCommand(messageText));
        SendMessage response = command.execute(update);
        return executeMessage(response);
    }

    public SendMessage executeMessage(SendMessage response) {
        try {
            execute(response);
            return response;
        } catch (TelegramApiException e) {
            throw new MessageExecutionException("Error executing message", e);
        }
    }

    private SendMessage sendErrorMessage(String chatId, String message) {
        SendMessage errorMessage = new SendMessage(chatId, message);
        executeMessage(errorMessage);
        return errorMessage;
    }
}