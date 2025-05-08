package com.example.personnel_management_service.repository;

import com.example.personnel_management_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Custom queries can be added here if needed

    // For example, you can query for departments by their IDs
    //Set<Department> findAllById(Iterable<Long> ids);
}

