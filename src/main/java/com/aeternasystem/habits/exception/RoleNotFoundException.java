package com.aeternasystem.habits.exception;

public class RoleNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The specified role was not found.";

    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
