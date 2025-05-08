package com.example.personnel_management_service.service;

import com.example.personnel_management_service.dto.DepartmentDto;
import com.example.personnel_management_service.dto.DepartmentRequest;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentRequest dto);
    DepartmentDto updateDepartment(Long id, DepartmentRequest dto);
    void deleteDepartment(Long id);
    DepartmentDto getDepartmentById(Long id);
    List<DepartmentDto> getAllDepartments();
}

