package com.aeternasystem.habits.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habit_logs")
public class HabitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Column(name = "log_date", nullable = false)
    private LocalDate date;

    public HabitLog(Habit habit, LocalDate date) {
        this.habit = habit;
        this.date = date;
    }
}