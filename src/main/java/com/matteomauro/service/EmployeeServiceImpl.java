package com.matteomauro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String EMPLOYEE_NOT_FOUND = "Employee Not Found";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
	}

	@Override
	public Employee insertNewEmployee(Employee employee) {
		if (employee != null)
			employee.setId(null);
		else
			throw new IllegalArgumentException("Employee must not be null");

		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployeeById(Long id, Employee replacement) throws EmployeeNotFoundException {
		if (replacement != null)
			replacement.setId(id);
		else
			throw new IllegalArgumentException("Employee must not be null");
		if (id == null)
			throw new IllegalArgumentException("Id must not be null");
		else if (id <= 0)
			throw new IllegalArgumentException("Id must not be less or equal to zero");

		return employeeRepository.save(replacement);
	}

	@Override
	public Employee updateEmployeeNameById(Long id, String name) throws EmployeeNotFoundException {
		Employee toUpdate = checkExistanceOfEmployeeById(id);
		toUpdate.setName(name);
		return employeeRepository.save(toUpdate);
	}

	@Override
	public Employee updateEmployeeLastNameById(Long id, String lastName) throws EmployeeNotFoundException {
		Employee toUpdate = checkExistanceOfEmployeeById(id);
		toUpdate.setLastName(lastName);
		return employeeRepository.save(toUpdate);
	}

	@Override
	public Employee updateEmployeeSalaryById(Long id, Long salary) throws EmployeeNotFoundException {
		if (salary <= 0)
			throw new IllegalArgumentException("Salary must not be less or equal to zero");
		Employee toUpdate = checkExistanceOfEmployeeById(id);
		toUpdate.setSalary(salary);
		return employeeRepository.save(toUpdate);
	}

	@Override
	public Employee updateEmployeeRoleById(Long id, String role) throws EmployeeNotFoundException {
		Employee toUpdate = checkExistanceOfEmployeeById(id);
		toUpdate.setRole(role);
		return employeeRepository.save(toUpdate);
	}

	@Override
	public void deleteById(Long id) throws EmployeeNotFoundException {
		if (id == null)
			throw new IllegalArgumentException("Id must not be null");

		checkExistanceOfEmployeeById(id);
		employeeRepository.deleteById(id);
	}

	private Employee checkExistanceOfEmployeeById(Long id) throws EmployeeNotFoundException {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
	}

}
