package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.InvalidAuthParamsException;
import com.aeternasystem.habits.exception.TelegramAuthException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class AuthenticationExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(InvalidAuthParamsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthParamsException(InvalidAuthParamsException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "INVALID_AUTH_PARAMS", ex.getMessage(), ex);
    }

    @ExceptionHandler(TelegramAuthException.class)
    public ResponseEntity<ErrorResponse> handleTelegramAuthException(TelegramAuthException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "TELEGRAM_AUTH_FAILED", ex.getMessage(), ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        String message = getErrorMessage(ex, "Authentication failed: invalid credentials.");
        return buildErrorResponseEntity(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_FAILED", message, ex);
    }
}