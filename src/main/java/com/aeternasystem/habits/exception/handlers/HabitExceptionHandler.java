package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.HabitAlreadyExistsException;
import com.aeternasystem.habits.exception.HabitLogAlreadyExistsException;
import com.aeternasystem.habits.exception.HabitLogNotFoundException;
import com.aeternasystem.habits.exception.HabitNotFoundException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class HabitExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(HabitAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleHabitAlreadyExistsException(HabitAlreadyExistsException ex) {
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "HABIT_ALREADY_EXISTS", ex.getMessage(), ex);
    }

    @ExceptionHandler(HabitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHabitNotFoundException(HabitNotFoundException ex) {
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "HABIT_NOT_FOUND", ex.getMessage(), ex);
    }

    @ExceptionHandler(HabitLogAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleHabitLogAlreadyExistsException(HabitLogAlreadyExistsException ex) {
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "HABIT_LOG_ALREADY_EXISTS", ex.getMessage(), ex);
    }

    @ExceptionHandler(HabitLogNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHabitLogNotFoundException(HabitLogNotFoundException ex) {
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "HABIT_LOG_NOT_FOUND", ex.getMessage(), ex);
    }
}