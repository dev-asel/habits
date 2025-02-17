package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.RoleAlreadyExistException;
import com.aeternasystem.habits.exception.RoleNotFoundException;
import com.aeternasystem.habits.exception.UserAlreadyExistException;
import com.aeternasystem.habits.exception.UserNotFoundException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class UserExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXISTS", ex.getMessage(), ex);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponseEntity(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", ex.getMessage(), ex);
    }

    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleRoleAlreadyExistException(RoleAlreadyExistException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "ROLE_ALREADY_EXISTS", ex.getMessage(), ex);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        return buildErrorResponseEntity(HttpStatus.NOT_FOUND, "ROLE_NOT_FOUND", ex.getMessage(), ex);
    }
}