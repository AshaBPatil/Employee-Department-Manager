package com.example.personnel_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
public class PersonnelManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonnelManagementServiceApplication.class, args);
	}

}
