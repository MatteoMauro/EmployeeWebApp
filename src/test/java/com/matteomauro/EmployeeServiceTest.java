package com.matteomauro;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;
import com.matteomauro.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	public void testGetAllEmployees() {
		Employee employee1 = new Employee(1L, "name1", "lastName1", 1000L, "role1");
		Employee employee2 = new Employee(2L, "name2", "lastName2", 2000L, "role2");
		when(employeeRepository.findAll()).thenReturn(asList(employee1, employee2));
		assertThat(employeeService.getAllEmployees()).containsExactly(employee1, employee2);
	}
	
	@Test
	public void testGetEmployeeByIdWhenIsPresent() throws Exception {
		Employee employee = new Employee(1L, "name", "lastName", 1000L, "role");
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		assertThat(employeeService.getEmployeeById(1L)).isEqualTo(employee);
	}

}
