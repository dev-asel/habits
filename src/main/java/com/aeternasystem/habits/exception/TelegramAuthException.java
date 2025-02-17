package com.aeternasystem.habits.exception;

public class TelegramAuthException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Telegram Auth Failed.";

    public TelegramAuthException() {
        super(DEFAULT_MESSAGE);
    }

    public TelegramAuthException(String message) {
        super(message);
    }

    public TelegramAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramAuthException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
