package com.matteomauro.rest_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllUsers() {
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/{id}")
	public Employee getById(@PathVariable Long id) throws EmployeeNotFoundException {
		return employeeService.getEmployeeById(id);
	}
}
