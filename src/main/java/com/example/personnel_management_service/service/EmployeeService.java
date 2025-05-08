package com.example.personnel_management_service.service;


import com.example.personnel_management_service.dto.EmployeeDto;
import com.example.personnel_management_service.dto.EmployeeRequest;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeRequest employeeRequest);
    EmployeeDto updateEmployee(Long id, EmployeeRequest employeeRequest);
    EmployeeDto getEmployeeById(Long id);
    void deleteEmployee(Long id);
    List<EmployeeDto> getAllEmployees();
}

