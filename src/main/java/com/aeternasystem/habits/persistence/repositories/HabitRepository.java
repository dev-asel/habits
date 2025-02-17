package com.aeternasystem.habits.persistence.repositories;

import com.aeternasystem.habits.persistence.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Optional<Habit> findById(Long id);
    List<Habit> findByUser_Id(Long userId);
    Optional<Habit> findByUser_IdAndName(Long userId, String name);

    void deleteById(Long id);
    void deleteByUser_IdAndName(Long userId, String name);

    @Query("SELECT h FROM Habit h LEFT JOIN FETCH h.logs WHERE h.id = :id")
    Optional<Habit> findByIdWithLogs(Long id);

    @Query("SELECT h FROM Habit h LEFT JOIN FETCH h.logs WHERE h.user.id = :userId AND h.name = :name")
    Optional<Habit> findByUserIdAndNameWithLogs(Long userId, String name);
}