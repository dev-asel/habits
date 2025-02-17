package com.aeternasystem.habits.exception;

public class EmptyUpdateException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Received update without message or text.";

    public EmptyUpdateException() {
        super(DEFAULT_MESSAGE);
    }

    public EmptyUpdateException(String message) {
        super(message);
    }

    public EmptyUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyUpdateException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}