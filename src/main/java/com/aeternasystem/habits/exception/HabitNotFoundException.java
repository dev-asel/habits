package com.aeternasystem.habits.exception;

public class HabitNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Habit not found.";

    public HabitNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public HabitNotFoundException(String message) {
        super(message);
    }

    public HabitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HabitNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}