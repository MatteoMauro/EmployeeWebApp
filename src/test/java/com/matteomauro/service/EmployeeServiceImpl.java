package com.matteomauro.service;

import java.util.List;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee insertNewEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateEmployeeById(Long id, Employee replacement) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateEmployeeNameById(Long id, String name) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateEmployeeLastNameById(Long id, String lastName) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateEmployeeSalaryById(Long id, Long salary) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateEmployeeRoleById(Long id, String role) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub

	}

}
