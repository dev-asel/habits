package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.AuthenticationPrincipalNotFoundException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class SecurityExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationPrincipalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationPrincipalNotFoundException(AuthenticationPrincipalNotFoundException ex) {
        return buildErrorResponseEntity(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_PRINCIPAL_NOT_FOUND", ex.getMessage(), ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String message = getErrorMessage(ex, "Authentication failed: invalid credentials.");
        return buildErrorResponseEntity(HttpStatus.FORBIDDEN, "ACCESS_DENIED", message, ex);
    }

    @ExceptionHandler({ExpiredJwtException.class, JwtException.class})
    public ResponseEntity<ErrorResponse> handleJwtExceptions(Exception ex) {
        HttpStatus status = ex instanceof ExpiredJwtException ? HttpStatus.UNAUTHORIZED : HttpStatus.BAD_REQUEST;
        String message = ex instanceof ExpiredJwtException
                ? "JWT token has expired."
                : "Invalid JWT token.";
        String errorCode = ex instanceof ExpiredJwtException
                ? "JWT_EXPIRED"
                : "INVALID_JWT";
        return buildErrorResponseEntity(status, errorCode, message, ex);
    }
}