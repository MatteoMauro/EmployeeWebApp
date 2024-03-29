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

	public EmployeeDTO(Long id, String name, String lastName, Long salary, String role) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.salary = salary;
		this.role = role;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
