package com.example.personnel_management_service.service;

import com.example.personnel_management_service.dto.DepartmentDto;
import com.example.personnel_management_service.dto.EmployeeDto;
import com.example.personnel_management_service.entity.Employee;
import com.example.personnel_management_service.entity.Department;
import com.example.personnel_management_service.entity.EmployeeDepartment;
import com.example.personnel_management_service.exception.DepartmentNotFoundException;
import com.example.personnel_management_service.exception.EmployeeNotFoundException;
import com.example.personnel_management_service.repository.EmployeeRepository;
import com.example.personnel_management_service.repository.DepartmentRepository;
import com.example.personnel_management_service.service.EmployeeDepartmentService;
import com.example.personnel_management_service.mapper.EmployeeMapper;
import com.example.personnel_management_service.mapper.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDepartmentServiceImpl implements EmployeeDepartmentService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional
    public void assignEmployeeToDepartments(Long employeeId, List<Long> departmentIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        List<Department> departments = departmentRepository.findAllById(departmentIds);

        Set<Long> existingDeptIds = employee.getDepartments().stream()
                .map(ed -> ed.getDepartment().getId())
                .collect(Collectors.toSet());

        Set<EmployeeDepartment> employeeDepartments = new HashSet<>();
        for (Department department : departments) {
            if (!existingDeptIds.contains(department.getId())) {
                EmployeeDepartment ed = new EmployeeDepartment();
                ed.setEmployee(employee);
                ed.setDepartment(department);
                employeeDepartments.add(ed);
            }
        }

        employee.getDepartments().addAll(employeeDepartments);
        employeeRepository.save(employee);
    }



    @Override
    @Transactional
    public void removeEmployeeFromDepartments(Long employeeId, List<Long> departmentIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        List<Department> departments = departmentRepository.findAllById(departmentIds);
        departments.forEach(employee.getDepartments()::remove);
        employeeRepository.save(employee);
    }

    @Override
    public List<DepartmentDto> getDepartmentsForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return employee.getDepartments().stream()
                .map(EmployeeDepartment::getDepartment) // ✅ get the actual Department
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<EmployeeDto> getEmployeesInDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        return department.getEmployees().stream()
                .map(EmployeeDepartment::getEmployee) // ✅ get the actual Employee
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

}

