package com.matteomauro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String TEMPORARY_IMPLEMENTATION = "Temporary implementation";

	@Override
	public List<Employee> getAllEmployees() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee insertNewEmployee(Employee employee) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee updateEmployeeById(Long id, Employee replacement) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee updateEmployeeNameById(Long id, String name) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee updateEmployeeLastNameById(Long id, String lastName) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee updateEmployeeSalaryById(Long id, Long salary) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public Employee updateEmployeeRoleById(Long id, String role) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	@Override
	public void deleteById(Long id) throws EmployeeNotFoundException {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

}
