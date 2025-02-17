package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.InvalidIdException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(0)
public class ValidationExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = getErrorMessage(ex, "An illegal or invalid argument was provided.");
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "ILLEGAL_ARGUMENT", message, ex);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdException(InvalidIdException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "INVALID_ID", ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = "Validation failed.";

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.merge(fieldName, errorMessage, (existing, newMessage) -> existing + "; " + newMessage);
        });

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            String objectName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.merge(objectName, errorMessage, (existing, newMessage) -> existing + "; " + newMessage);
        });

        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message, ex, errors);
    }
}