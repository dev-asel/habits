package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.DataInitializationException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(2)
public class GeneralExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(DataInitializationException.class)
    public ResponseEntity<ErrorResponse> handleDataInitializationException(DataInitializationException ex) {
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "DATA_INITIALIZATION_ERROR", ex.getMessage(), ex);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(RuntimeException ex) {
        String message = getErrorMessage(ex, "An unexpected error occurred.");
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR", message, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        String message = getErrorMessage(ex, "An unexpected error occurred.");
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", message, ex);
    }
}
