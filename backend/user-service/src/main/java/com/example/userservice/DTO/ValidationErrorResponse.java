package com.example.userservice.DTO;

import java.time.LocalDateTime;
import java.util.List;


public class ValidationErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<ValidationError> errors;

    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public LocalDateTime getTimestamp() {return timestamp;}

    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}

    public List<ValidationError> getErrors() {return errors;}

    public void setErrors(List<ValidationError> errors) {this.errors = errors;}

    public ValidationErrorResponse(int status, String message, List<ValidationError> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    // Внутрішній клас для конкретного поля
    public static class ValidationError {
        private String field;
        private String message;

        public String getField() {return field;}

        public void setField(String field) {this.field = field;}

        public String getMessage() {return message;}

        public void setMessage(String message) {this.message = message;}

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}