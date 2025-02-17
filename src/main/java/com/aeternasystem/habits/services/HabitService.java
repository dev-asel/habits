package com.aeternasystem.habits.services;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface HabitService {
    Habit save(Habit habit);
    Habit createHabit(Habit habit);
    Habit create(Habit habit);
    Habit create(Long userId, String name);
    Habit updateHabit(Long id, User newUser, String name);
    Habit update(Habit habit);
    Habit updateName(Long id, String name);
    void checkHabitNotExists(Long userId, String name);
    Optional<Habit> findById(Long id);
    Optional<Habit> findByIdWithLogs(Long id);
    Habit getById(Long id);
    List<Habit> findByUserId(Long userId);
    Optional<Habit> findByUserIdAndName(Long userId, String name);
    Optional<Habit> findByUserIdAndNameWithLogs(Long userId, String name);
    Habit getByUserIdAndName(Long userId, String name);
    List<Habit> findAll();
    void delete(Habit habit);
    void delete(Long id);
    void delete(Long userId, String name);
}