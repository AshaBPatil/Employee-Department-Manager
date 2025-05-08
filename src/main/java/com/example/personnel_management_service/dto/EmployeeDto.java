package com.example.personnel_management_service.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        Set<Long> departmentIds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {}
