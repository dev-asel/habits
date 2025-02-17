package com.aeternasystem.habits.exception;

public class DataInitializationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Data initialization failed.";

    public DataInitializationException() {
        super(DEFAULT_MESSAGE);
    }

    public DataInitializationException(String message) {
        super(message);
    }

    public DataInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInitializationException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}