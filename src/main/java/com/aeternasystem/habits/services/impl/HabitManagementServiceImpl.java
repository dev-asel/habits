package com.aeternasystem.habits.services.impl;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.services.HabitManagementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitManagementServiceImpl implements HabitManagementService {
    @Override
    public void addHabits(User user, List<Habit> habits) {
        user.getHabits().addAll(habits);
    }

    @Override
    public void removeHabits(User user) {
        user.getHabits().clear();
    }
}