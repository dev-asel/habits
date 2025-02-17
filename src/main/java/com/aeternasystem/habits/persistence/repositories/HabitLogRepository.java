package com.aeternasystem.habits.persistence.repositories;

import com.aeternasystem.habits.persistence.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    Optional<HabitLog> findById(Long id);
    Optional<HabitLog> findByHabit_IdAndDate(Long habitId, LocalDate date);
    List<HabitLog> findByHabit_Id(Long habitId);
    List<HabitLog> findByHabit_User_Id(Long userId);

    List<HabitLog> findByHabitIdAndDate(Long habitId, LocalDate date);
    void deleteByHabitIdAndDate(Long habitId, LocalDate date);

    @Query("SELECT hl FROM HabitLog hl WHERE hl.habit.id = :habitId AND EXTRACT(YEAR FROM hl.date) = :year AND EXTRACT(MONTH FROM hl.date) = :month")
    List<HabitLog> findByHabitIdAndMonth(@Param("habitId") Long habitId, @Param("year") int year, @Param("month") int month);
}