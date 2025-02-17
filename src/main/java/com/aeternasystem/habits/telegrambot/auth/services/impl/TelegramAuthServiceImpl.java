package com.aeternasystem.habits.telegrambot.auth.services.impl;

import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.security.jwt.TokenGenerationService;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.telegrambot.auth.services.TelegramAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TelegramAuthServiceImpl implements TelegramAuthService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramAuthServiceImpl.class);

    private final UserService userService;
    private final TokenGenerationService tokenGenerationService;

    public TelegramAuthServiceImpl(UserService userService, TokenGenerationService tokenGenerationService) {
        this.userService = userService;
        this.tokenGenerationService = tokenGenerationService;
    }

    @Override
    public void authenticateUserAndSetToken(HttpServletResponse response, String chatId, String name) {
        User user = userService.register(chatId, name);
        tokenGenerationService.generateAndSetToken(response, new CustomUserDetails(user));
    }
}