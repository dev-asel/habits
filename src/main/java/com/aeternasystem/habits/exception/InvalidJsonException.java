package com.aeternasystem.habits.exception;

public class InvalidJsonException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The provided JSON is invalid or improperly formatted.";

    public InvalidJsonException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidJsonException(String message) {
        super(message);
    }

    public InvalidJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJsonException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}