package com.example.personnel_management_service.mapper;


import com.example.personnel_management_service.dto.DepartmentDto;
import com.example.personnel_management_service.dto.DepartmentRequest;
import com.example.personnel_management_service.entity.Department;
import com.example.personnel_management_service.entity.EmployeeDepartment;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface DepartmentMapper {

    DepartmentDto toDto(Department entity);

    Department toEntity(DepartmentRequest request);

    //Department toEntity(DepartmentDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Department entity, DepartmentRequest request);

    default Set<Long> map(Set<EmployeeDepartment> employeeDepartments) {
        if (employeeDepartments == null) return null;
        return employeeDepartments.stream()
                .map(ed -> ed.getEmployee().getId())
                .collect(Collectors.toSet());
    }
}

