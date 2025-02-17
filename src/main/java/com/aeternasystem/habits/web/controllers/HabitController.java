package com.aeternasystem.habits.web.controllers;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.mapper.HabitMapper;
import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.services.HabitService;
import com.aeternasystem.habits.web.dto.HabitDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/habits")
public class HabitController {

    private static final Logger logger = LoggerFactory.getLogger(HabitController.class);

    private final HabitService habitService;
    private final HabitMapper habitMapper;
    private final String homepageUrl;
    private static final String HABITS = "habits";
    private static final String HABITS_PAGE = HABITS;
    private static final String ATTR_HABITS = HABITS;
    private static final String ATTR_NAME = "name";
    private static final String ATTR_USER_ID = "userId";
    private static final String ATTR_HOMEPAGE_URL = "homepageUrl";


    public HabitController(HabitService habitService, HabitMapper habitMapper, ResourcesProperties resourcesProperties) {
        this.habitService = habitService;
        this.habitMapper = habitMapper;
        this.homepageUrl = resourcesProperties.getHomepageUrl();
    }

    @GetMapping
    public String getHabitsPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Long userId = userDetails.getUserId();
        String name = userDetails.getName();

        List<Habit> habits = habitService.findByUserId(userId);
        List<HabitDTO> habitDTOs = habitMapper.toDTOs(habits);

        model.addAttribute(ATTR_HABITS, habitDTOs);
        model.addAttribute(ATTR_NAME, name);
        model.addAttribute(ATTR_USER_ID, userId);
        model.addAttribute(ATTR_HOMEPAGE_URL, homepageUrl);

        return HABITS_PAGE;
    }
}