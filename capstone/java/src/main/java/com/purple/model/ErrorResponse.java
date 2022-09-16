package com.purple.model;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    public ErrorResponse(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    public ErrorResponse(String message) {
        this.errorMessages = new HashMap<>();
        this.errorMessages.put("error",message);
    }

    private Map<String, String> errorMessages;

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
