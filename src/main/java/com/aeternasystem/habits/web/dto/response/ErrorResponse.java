package com.aeternasystem.habits.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private Map<String, String> details;

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.details = new HashMap<>();
    }

    public ErrorResponse(String errorCode, String errorMessage, Map<String, String> details) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.details = details;
    }
}