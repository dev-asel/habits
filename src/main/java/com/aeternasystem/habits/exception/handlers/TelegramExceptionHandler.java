package com.aeternasystem.habits.exception.handlers;

import com.aeternasystem.habits.exception.EmptyUpdateException;
import com.aeternasystem.habits.exception.InvalidCommandException;
import com.aeternasystem.habits.exception.MessageExecutionException;
import com.aeternasystem.habits.web.dto.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ControllerAdvice
@Order(1)
public class TelegramExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(TelegramApiException.class)
    public ResponseEntity<ErrorResponse> handleTelegramApiException(TelegramApiException ex) {
        String message = getErrorMessage(ex, "An error occurred while interacting with the Telegram API.");
        return buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "TELEGRAM_API_ERROR", message, ex);
    }

    @ExceptionHandler(InvalidCommandException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommandException(InvalidCommandException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "INVALID_COMMAND", ex.getMessage(), ex);
    }

    @ExceptionHandler(EmptyUpdateException.class)
    public ResponseEntity<ErrorResponse> handleEmptyUpdateException(EmptyUpdateException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "EMPTY_UPDATE", ex.getMessage(), ex);
    }

    @ExceptionHandler(MessageExecutionException.class)
    public ResponseEntity<ErrorResponse> handleMessageExecutionException(MessageExecutionException ex) {
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, "MESSAGE_EXECUTION_ERROR", ex.getMessage(), ex);
    }
}