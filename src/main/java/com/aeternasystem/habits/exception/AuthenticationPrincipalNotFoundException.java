package com.aeternasystem.habits.exception;

public class AuthenticationPrincipalNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Authentication principal not found.";

    public AuthenticationPrincipalNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public AuthenticationPrincipalNotFoundException(String message) {
        super(message);
    }

    public AuthenticationPrincipalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationPrincipalNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}