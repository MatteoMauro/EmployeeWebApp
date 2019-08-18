package com.matteomauro.dto;

import java.io.Serializable;

import com.matteomauro.model.Employee;

public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 1638307411819534361L;

	private Long id;
	private String name;
	private String lastName;
	private Long salary;
	private String role;

	public EmployeeDTO(Employee employee) {
		this.id = employee.getId();
		this.name = employee.getName();
		this.lastName = employee.getLastName();
		this.salary = employee.getSalary();
		this.role = employee.getRole();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

}
