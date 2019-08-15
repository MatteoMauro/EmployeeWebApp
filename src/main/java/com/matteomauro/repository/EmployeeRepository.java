package com.matteomauro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matteomauro.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
