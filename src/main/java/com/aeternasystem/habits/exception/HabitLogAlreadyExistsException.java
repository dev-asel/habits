package com.aeternasystem.habits.exception;

public class HabitLogAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "HabitLog already exists.";

    public HabitLogAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public HabitLogAlreadyExistsException(String message) {
        super(message);
    }

    public HabitLogAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public HabitLogAlreadyExistsException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}