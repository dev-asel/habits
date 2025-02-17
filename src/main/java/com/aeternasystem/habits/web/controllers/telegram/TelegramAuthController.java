package com.aeternasystem.habits.web.controllers.telegram;

import com.aeternasystem.habits.telegrambot.auth.services.TelegramAuthService;
import com.aeternasystem.habits.telegrambot.auth.services.impl.TelegramAuthValidationService;
import com.aeternasystem.habits.telegrambot.auth.util.TelegramParamsUtil;
import com.aeternasystem.habits.web.dto.TelegramAuthDTO;
import com.aeternasystem.habits.web.dto.response.RedirectResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.logging.Logger;

@Controller
public class TelegramAuthController {

    private static final Logger logger = Logger.getLogger(TelegramAuthController.class.getName());

    private final TelegramAuthService telegramAuthService;
    private final TelegramAuthValidationService telegramAuthValidationService;
    private static final String HABITS_PATH = "/habits";

    public TelegramAuthController(TelegramAuthService telegramAuthService, TelegramAuthValidationService telegramAuthValidationService) {
        this.telegramAuthService = telegramAuthService;
        this.telegramAuthValidationService = telegramAuthValidationService;
    }

    @PostMapping("/auth/app/callback")
    public ResponseEntity<RedirectResponse> authenticateViaMiniApp(@RequestBody TelegramAuthDTO telegramAuthDTO, HttpServletResponse response) {
        Map<String, String> authParams = TelegramParamsUtil.getAuthParams(telegramAuthDTO.getInitData());
        telegramAuthValidationService.validateMiniAppAuth(authParams);
        Map<String, String> userParams = TelegramParamsUtil.getUserParams(authParams.get("user"));

        telegramAuthService.authenticateUserAndSetToken(response, userParams.get("id"), userParams.get("first_name"));
        return ResponseEntity.ok(new RedirectResponse(true, HABITS_PATH));
    }

    @GetMapping("/auth/telegram/callback")
    public String authenticateViaWidget(@RequestParam Map<String, String> authParams, HttpServletResponse response) {
        telegramAuthValidationService.validateWidgetAuth(authParams);

        telegramAuthService.authenticateUserAndSetToken(response, authParams.get("id"), authParams.get("first_name"));
        return "redirect:" + HABITS_PATH;
    }
}