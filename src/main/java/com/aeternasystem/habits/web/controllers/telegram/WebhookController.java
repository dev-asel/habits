package com.aeternasystem.habits.web.controllers.telegram;


import com.aeternasystem.habits.telegrambot.TelegramBot;
import com.aeternasystem.habits.telegrambot.validation.TelegramBotValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private final TelegramBot telegramBot;
    private final TelegramBotValidator validator;

    public WebhookController(TelegramBot telegramBot, TelegramBotValidator validator) {
        this.telegramBot = telegramBot;
        this.validator = validator;
    }

    @PostMapping("/telegram/webhook")
    public ResponseEntity<Void> onUpdateReceived(@RequestBody Update update) {
        logger.info("Received update: {}", update);
        try {
            validator.validateUpdate(update);
            telegramBot.handleUpdate(update);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error handling webhook update: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}