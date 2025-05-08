package com.example.personnel_management_service.controller;


import com.example.personnel_management_service.dto.EmployeeDto;
import com.example.personnel_management_service.service.EmployeeDepartmentService;
import com.example.personnel_management_service.service.EmployeeService;
import com.example.personnel_management_service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees/{employeeId}/departments")
@RequiredArgsConstructor
public class EmployeeDepartmentController {

    private final EmployeeDepartmentService employeeDepartmentService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> assignEmployeeToDepartments(
            @PathVariable Long employeeId,
            @RequestBody List<Long> departmentIds) {
        employeeDepartmentService.assignEmployeeToDepartments(employeeId, departmentIds);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployeesInDepartment(@PathVariable Long employeeId) {
        List<EmployeeDto> employees = employeeDepartmentService.getEmployeesInDepartment(employeeId);
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> removeEmployeeFromDepartments(
            @PathVariable Long employeeId,
            @RequestBody List<Long> departmentIds) {
        employeeDepartmentService.removeEmployeeFromDepartments(employeeId, departmentIds);
        return ResponseEntity.noContent().build();
    }
}

