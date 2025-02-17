package com.aeternasystem.habits.exception;

public class UserNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "The specified user was not found.";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}