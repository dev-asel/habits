package com.aeternasystem.habits.exception;


public class InvalidAuthParamsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Authentication parameters are missing or invalid.";

    public InvalidAuthParamsException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidAuthParamsException(String message) {
        super(message);
    }

    public InvalidAuthParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthParamsException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}