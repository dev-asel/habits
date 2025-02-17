package com.aeternasystem.habits.services;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.HabitLog;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface HabitLogService {
    HabitLog save(HabitLog habitLog);
    HabitLog createHabitLog(HabitLog habitLog);
    HabitLog create(HabitLog habitLog);

    HabitLog create(Long habitId, LocalDate date);

    HabitLog updateHabitLog(Long id, Habit newHabit, LocalDate date);
    HabitLog update(HabitLog habitLog);

    HabitLog updateDate(Long id, LocalDate date);

    void checkHabitLogNotExists(Long habitId, LocalDate date);
    Optional<HabitLog> findById(Long id);
    HabitLog getById(Long id);
    Optional<HabitLog> findByHabitIdAndDate(Long habitId, LocalDate date);
    HabitLog getByHabitIdAndDate(Long habitId, LocalDate date);
    List<HabitLog> findAllByHabitId(Long habitId);
    List<HabitLog> findAllByUserId(Long userId);
    List<HabitLog> findAll();
    void delete(HabitLog habitLog);

    void delete(Long habitId, LocalDate date);

    void delete(Long id);

    void deleteByHabitId(Long habitId);

    List<HabitLog> findByUserIdForToday(Long userId);

    List<HabitLog> findByHabitIdAndMonth(Long habitId, YearMonth yearMonth);
}