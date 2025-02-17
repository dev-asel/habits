package com.aeternasystem.habits.exception;


public class InvalidIdException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The provided ID is invalid or does not match the expected format.";

    public InvalidIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}