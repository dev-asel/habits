package com.aeternasystem.habits.exception;

public class MessageExecutionException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error executing message.";

    public MessageExecutionException() {
        super(DEFAULT_MESSAGE);
    }

    public MessageExecutionException(String message) {
        super(message);
    }

    public MessageExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageExecutionException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}