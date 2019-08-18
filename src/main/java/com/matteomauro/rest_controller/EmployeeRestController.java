package com.matteomauro.rest_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matteomauro.dto.EmployeeDTO;
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

	@PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Employee newEmployee(@RequestBody EmployeeDTO employeeDto) {
		return employeeService.insertNewEmployee(employeeDto.getEmployee());
	}

	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Employee updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeDTO employeeDtoReplacement)
			throws EmployeeNotFoundException {
		return employeeService.updateEmployeeById(id, employeeDtoReplacement.getEmployee());
	}

	@PatchMapping(value = "/update/name/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Employee updateEmployeeNameById(@PathVariable Long id, @RequestBody String newName)
			throws EmployeeNotFoundException {
		return employeeService.updateEmployeeNameById(id, newName);
	}
}
