package com.aeternasystem.habits.exception;


public class HabitAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Habit already exists.";

    public HabitAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public HabitAlreadyExistsException(String message) {
        super(message);
    }

    public HabitAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public HabitAlreadyExistsException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}