package com.example.personnel_management_service.exception;

public class DepartmentNotFoundException extends DomainException {
    public DepartmentNotFoundException(Long id) {
        super("Department with ID " + id + " not found.");
    }
}

