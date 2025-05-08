package com.example.personnel_management_service.service;

import com.example.personnel_management_service.dto.DepartmentDto;
import com.example.personnel_management_service.dto.DepartmentRequest;
import com.example.personnel_management_service.exception.DepartmentNotFoundException;
import com.example.personnel_management_service.mapper.DepartmentMapper;
import com.example.personnel_management_service.entity.Department;
import com.example.personnel_management_service.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDto createDepartment(DepartmentRequest dto) {
        Department department = departmentMapper.toEntity(dto);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toDto(saved);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentRequest dto) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        departmentMapper.updateEntity(existing, dto);
        Department updated = departmentRepository.save(existing);
        return departmentMapper.toDto(updated);
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        return departmentMapper.toDto(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDto)
                .toList();
    }

}


