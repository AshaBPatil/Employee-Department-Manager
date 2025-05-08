package com.example.personnel_management_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record EmployeeRequest(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @Email(message = "Invalid email format") String email,
        @NotNull(message = "Department IDs cannot be null") Set<Long> departmentIds
) {}
