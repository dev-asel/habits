package com.aeternasystem.habits.web.controllers;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.util.RoleUtil;
import com.aeternasystem.habits.web.dto.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class LoginController {

    public static final String TELEGRAM_WIDGET_URL = "https://telegram.org/js/telegram-widget.js?22";
    private final String telegramBotUrl;
    private final String telegramBotUsername;
    private final String telegramOauthUrl;
    private final String homepageUrl;
    private static final String USERS_PATH = "/users";
    private static final String HABITS_PATH = "/habits";
    private static final String ERROR_PAGE = "error";
    private static final String ATTR_HOMEPAGE_URL = "homepageUrl";

    LoginController(ResourcesProperties resourcesProperties) {
        this.telegramBotUrl = resourcesProperties.getTelegramBotUrl();
        this.telegramBotUsername = resourcesProperties.getTelegramBotUsername();
        this.telegramOauthUrl = resourcesProperties.getTelegramOAuthUrl();
        this.homepageUrl = resourcesProperties.getHomepageUrl();
    }

    @GetMapping("/")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("telegramBotUrl", telegramBotUrl);
        model.addAttribute("telegramBotUsername", telegramBotUsername);
        model.addAttribute("telegramOAuthUrl", telegramOauthUrl);
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute(ATTR_HOMEPAGE_URL, homepageUrl);
        model.addAttribute("telegramWidgetUrl", TELEGRAM_WIDGET_URL);
        return "login";
    }

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView("/", true);
    }

    @GetMapping("/loginSuccess")
    public RedirectView loginSuccess(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String redirectUrl = RoleUtil.isAdmin(userDetails.getRoles()) ? USERS_PATH : HABITS_PATH;
        return new RedirectView(redirectUrl, true);
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute(ATTR_HOMEPAGE_URL, homepageUrl);
        return ERROR_PAGE;
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("errorMessage", "Access Denied");
        model.addAttribute(ATTR_HOMEPAGE_URL, homepageUrl);
        return ERROR_PAGE;
    }
}