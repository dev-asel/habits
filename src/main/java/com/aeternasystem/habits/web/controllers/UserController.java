package com.aeternasystem.habits.web.controllers;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final String homepageUrl;
    private static final String USERS = "users";
    private static final String USERS_PAGE = USERS;
    private static final String ATTR_USERS = USERS;
    private static final String ATTR_HOMEPAGE_URL = "homepageUrl";

    public UserController(UserService userService, ResourcesProperties resourcesProperties) {
        this.userService = userService;
        this.homepageUrl = resourcesProperties.getHomepageUrl();
    }

    @GetMapping
    public String getUsersPage(Model model) {
        model.addAttribute(ATTR_USERS, userService.findAll());
        model.addAttribute(ATTR_HOMEPAGE_URL, homepageUrl);

        return USERS_PAGE;
    }
}