package com.matteomauro.dto;

import com.matteomauro.model.Employee;

public class EmployeeDTO {

	private Long id;
	private String name;
	private String lastName;
	private Long salary;
	private String role;

	public EmployeeDTO() {
	}

	public EmployeeDTO(Employee employee) {
		this.id = employee.getId();
		this.name = employee.getName();
		this.lastName = employee.getLastName();
		this.salary = employee.getSalary();
		this.role = employee.getRole();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public Long getSalary() {
		return salary;
	}

	public String getRole() {
		return role;
	}

	public Employee getEmployee() {
		return new Employee(id, name, lastName, salary, role);
	}

}
