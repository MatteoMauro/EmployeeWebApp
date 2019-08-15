package com.matteomauro;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
		Employee employee1 = new Employee(null, "name1", "lastName1", 1000L, "role1");
		Employee employee2 = new Employee(null, "name2", "lastName2", 2000L, "role2");
		when(employeeRepository.findAll()).thenReturn(asList(employee1, employee2));
		assertThat(employeeService.getAllEmployees()).containsExactly(employee1, employee2);
	}

}
