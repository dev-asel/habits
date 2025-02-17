package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.CryptographyException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class CryptographyExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CryptographyException.class)
    public ResponseEntity<ErrorResponse> handleCryptographyException(CryptographyException ex) {
        HttpStatus status = ex.getStatus();
        String message = getErrorMessage(ex, "Cryptography error");
        return buildErrorResponseEntity(status, "CRYPTOGRAPHY_ERROR", message, ex);
    }
}