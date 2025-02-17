package com.aeternasystem.habits.exception;

public class HabitLogNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "HabitLog not found.";

    public HabitLogNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public HabitLogNotFoundException(String message) {
        super(message);
    }

    public HabitLogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HabitLogNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}