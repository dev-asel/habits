package com.aeternasystem.habits.exception;

public class UserAlreadyExistException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "User already exists.";

    public UserAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}