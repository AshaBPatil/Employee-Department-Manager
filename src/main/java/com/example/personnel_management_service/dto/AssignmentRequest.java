package com.example.personnel_management_service.dto;

import java.util.List;

public record AssignmentRequest(
        List<Long> departmentIds
) {}

