package com.example.personnel_management_service.dto;

import java.time.LocalDateTime;

public record DepartmentDto(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {}

