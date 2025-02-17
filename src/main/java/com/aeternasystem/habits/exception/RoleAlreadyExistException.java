package com.aeternasystem.habits.exception;

public class RoleAlreadyExistException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Role already exists.";

    public RoleAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public RoleAlreadyExistException(String message) {
        super(message);
    }

    public RoleAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleAlreadyExistException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
