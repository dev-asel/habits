package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;
import java.util.Optional;

@ControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    protected ResponseEntity<ErrorResponse> buildErrorResponseEntity(HttpStatus status, String errorCode, String message, Exception ex) {
        logger.error("{}: {}", errorCode, ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(errorCode, message), status);
    }

    protected ResponseEntity<ErrorResponse> buildErrorResponseEntity(HttpStatus status, String errorCode, String message, Exception ex, Map<String, String> details) {
        logger.error("{}: {}", errorCode, ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(errorCode, message, details), status);
    }

    protected String getErrorMessage(Exception ex, String defaultMessage) {
        return Optional.ofNullable(ex.getMessage()).orElse(defaultMessage);
    }
}