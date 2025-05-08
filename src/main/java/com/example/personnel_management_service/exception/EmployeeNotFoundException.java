package com.example.personnel_management_service.exception;

public class EmployeeNotFoundException extends DomainException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with ID " + id + " not found.");
    }
}

