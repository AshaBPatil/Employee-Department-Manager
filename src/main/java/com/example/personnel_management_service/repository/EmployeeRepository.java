package com.example.personnel_management_service.repository;

import com.example.personnel_management_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom queries can be added here if needed
}

