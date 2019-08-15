package com.matteomauro.service;

import java.util.List;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees();

	public Employee getEmployeeById(Long id) throws EmployeeNotFoundException;

	public Employee insertNewEmployee(Employee employee);

	public Employee updateEmployeeById(Long id, Employee replacement) throws EmployeeNotFoundException;

	public Employee updateEmployeeNameById(Long id, String name) throws EmployeeNotFoundException;

	public Employee updateEmployeeLastNameById(Long id, String lastName) throws EmployeeNotFoundException;

	public Employee updateEmployeeSalaryById(Long id, Long salary) throws EmployeeNotFoundException;

	public Employee updateEmployeeRoleById(Long id, String role) throws EmployeeNotFoundException;

	public void deleteById(Long id) throws EmployeeNotFoundException;

}
