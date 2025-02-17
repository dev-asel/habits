package com.aeternasystem.habits.exception;

public class JsonConversionException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Json conversion error.";

    public JsonConversionException() {
        super(DEFAULT_MESSAGE);
    }

    public JsonConversionException(String message) {
        super(message);
    }

    public JsonConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonConversionException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
