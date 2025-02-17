package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.InvalidJsonException;
import com.aeternasystem.habits.exception.JsonConversionException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class SerializationExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(InvalidJsonException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJsonException(InvalidJsonException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "INVALID_JSON", ex.getMessage(), ex);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> handleJsonProcessingException(JsonProcessingException ex) {
        String message = getErrorMessage(ex, "An error occurred while processing the JSON data.");
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "JSON_PROCESSING_ERROR", message, ex);
    }

    @ExceptionHandler(JsonConversionException.class)
    public ResponseEntity<ErrorResponse> handleJsonConversionException(JsonConversionException ex) {
        String message = getErrorMessage(ex, "An error occurred while converting the JSON data.");
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "JSON_CONVERSION_ERROR", message, ex);
    }
}