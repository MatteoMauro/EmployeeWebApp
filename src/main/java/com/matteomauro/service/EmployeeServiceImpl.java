package com.matteomauro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;

public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
	}

	@Override
	public Employee insertNewEmployee(Employee employee) {
		if(employee!=null)
			employee.setId(null);
		
		return employeeRepository.save(employee);
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
