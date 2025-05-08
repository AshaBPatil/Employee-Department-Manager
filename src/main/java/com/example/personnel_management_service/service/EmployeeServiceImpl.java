package com.example.personnel_management_service.service;

import com.example.personnel_management_service.dto.EmployeeDto;
import com.example.personnel_management_service.dto.EmployeeRequest;
import com.example.personnel_management_service.entity.Department;
import com.example.personnel_management_service.entity.Employee;
import com.example.personnel_management_service.entity.EmployeeDepartment;
import com.example.personnel_management_service.exception.DepartmentNotFoundException;
import com.example.personnel_management_service.exception.EmployeeNotFoundException;
import com.example.personnel_management_service.kafka.DomainEventPublisher;
import com.example.personnel_management_service.mapper.EmployeeMapper;
import com.example.personnel_management_service.repository.DepartmentRepository;
import com.example.personnel_management_service.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;
    private final DomainEventPublisher domainEventPublisher; // ✅ Injected

    @Override
    public EmployeeDto createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);

        Set<EmployeeDepartment> employeeDepartments = resolveEmployeeDepartments(employeeRequest.departmentIds(), employee);
        employee.setDepartments(employeeDepartments);

        Employee saved = employeeRepository.save(employee);
        EmployeeDto dto = employeeMapper.toDto(saved);

        domainEventPublisher.publish("EmployeeCreated", dto); // ✅ Publish

        return dto;
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeMapper.updateEntity(existing, employeeRequest);

        Set<EmployeeDepartment> employeeDepartments = resolveEmployeeDepartments(employeeRequest.departmentIds(), existing);
        existing.getDepartments().clear();
        existing.getDepartments().addAll(employeeDepartments);

        Employee updated = employeeRepository.save(existing);
        EmployeeDto dto = employeeMapper.toDto(updated);

        domainEventPublisher.publish("EmployeeUpdated", dto); // ✅ Publish

        return dto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employeeMapper.toDto(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeRepository.delete(employee);

        EmployeeDto dto = employeeMapper.toDto(employee);
        domainEventPublisher.publish("EmployeeDeleted", dto); // ✅ Publish
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    private Set<EmployeeDepartment> resolveEmployeeDepartments(Set<Long> departmentIds, Employee employee) {
        Set<EmployeeDepartment> employeeDepartments = new HashSet<>();
        for (Long departmentId : departmentIds) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
            EmployeeDepartment ed = new EmployeeDepartment();
            ed.setEmployee(employee);
            ed.setDepartment(department);
            employeeDepartments.add(ed);
        }
        return employeeDepartments;
    }
}
