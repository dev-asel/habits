package com.aeternasystem.habits.exception;

public class InvalidCommandException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The command provided is invalid or not recognized.";

    public InvalidCommandException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}