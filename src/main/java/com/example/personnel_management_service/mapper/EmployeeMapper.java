package com.example.personnel_management_service.mapper;

import com.example.personnel_management_service.dto.EmployeeDto;
import com.example.personnel_management_service.dto.EmployeeRequest;
import com.example.personnel_management_service.entity.Department;
import com.example.personnel_management_service.entity.Employee;
import com.example.personnel_management_service.entity.EmployeeDepartment;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface EmployeeMapper {

    // ✅ Map departments to departmentIds when converting to DTO
    @Mapping(target = "departmentIds", source = "departments")
    EmployeeDto toDto(Employee entity);

    // 🚫 Don't map departmentIds here; it's resolved manually in the service
    @Mapping(target = "departments", ignore = true)
    Employee toEntity(EmployeeRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "departments", ignore = true)
    void updateEntity(@MappingTarget Employee entity, EmployeeRequest employeeRequest);

    // ✅ Used by MapStruct to map Set<EmployeeDepartment> to Set<Long>
    default Set<Long> map(Set<EmployeeDepartment> employeeDepartments) {
        if (employeeDepartments == null) return null;
        return employeeDepartments.stream()
                .map(ed -> ed.getDepartment().getId())
                .collect(Collectors.toSet());
    }

    // 🟡 Utility method (not directly used by MapStruct)
    default Set<Long> mapDepartmentsToIds(Set<Department> departments) {
        return departments == null ? null :
                departments.stream()
                        .map(Department::getId)
                        .collect(Collectors.toSet());
    }

    // 🛠️ Only for service layer use, not for MapStruct
    default Set<Department> mapIdsToDepartments(Set<Long> ids) {
        return null; // Should be implemented in service with DepartmentRepository
    }
}
