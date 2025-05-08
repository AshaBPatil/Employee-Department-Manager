package com.example.personnel_management_service.exception;

public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}

