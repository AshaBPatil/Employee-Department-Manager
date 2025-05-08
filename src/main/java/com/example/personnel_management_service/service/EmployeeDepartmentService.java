package com.example.personnel_management_service.service;

import com.example.personnel_management_service.dto.DepartmentDto;
import com.example.personnel_management_service.dto.EmployeeDto;

import java.util.List;

public interface EmployeeDepartmentService {
    void assignEmployeeToDepartments(Long employeeId, List<Long> departmentIds);
    void removeEmployeeFromDepartments(Long employeeId, List<Long> departmentIds);
    List<DepartmentDto> getDepartmentsForEmployee(Long employeeId);
    List<EmployeeDto> getEmployeesInDepartment(Long departmentId);
}

