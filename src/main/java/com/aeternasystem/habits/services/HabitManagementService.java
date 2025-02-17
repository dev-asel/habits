package com.aeternasystem.habits.services;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.User;

import java.util.List;

public interface HabitManagementService {
    void addHabits(User user, List<Habit> habits);

    void removeHabits(User user);
}
