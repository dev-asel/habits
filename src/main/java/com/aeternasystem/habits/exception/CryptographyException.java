package com.aeternasystem.habits.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CryptographyException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cryptography error.";
    private final HttpStatus status;

    public CryptographyException(HttpStatus status) {
        super(DEFAULT_MESSAGE);
        this.status = status;
    }

    public CryptographyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CryptographyException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public CryptographyException(Throwable cause, HttpStatus status) {
        super(DEFAULT_MESSAGE, cause);
        this.status = status;
    }
}