package com.matteomauro.rest_controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.service.EmployeeService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRestControllerTest {

	@InjectMocks
	private EmployeeRestController employeeRestController;

	@Mock
	private EmployeeService employeeService;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(employeeRestController);
	}

	@Test
	public void testFindAllUsersWithEmptyDatabase() {
		when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

		given().
		when().
			get("/api/employees").
		then().
			statusCode(200).
			assertThat().body(is(equalTo("[]")));
	}

	@Test
	public void testFindAllUsersWithExistingEmployees() {
		Employee employee1 = new Employee(1L, "name1", "lastName1", 1000L, "role1");
		Employee employee2 = new Employee(2L, "name2", "lastName2", 2000L, "role2");
		when(employeeService.getAllEmployees()).thenReturn(asList(employee1, employee2));

		given().
		when().
			get("/api/employees").
		then().
			statusCode(200).
			assertThat().
			body("id[0]", equalTo(1), 
				 "name[0]", equalTo("name1"), 
				 "lastName[0]", equalTo("lastName1"), 
				 "salary[0]", equalTo(1000),
				 "role[0]", equalTo("role1"),
				 "id[1]", equalTo(2), 
				 "name[1]", equalTo("name2"), 
				 "lastName[1]", equalTo("lastName2"), 
				 "salary[1]", equalTo(2000),
				 "role[1]", equalTo("role2"));
	}
	
	@Test
	public void testFindEmployeeById_whenFound() throws EmployeeNotFoundException {
		when(employeeService.getEmployeeById(1L)).
			thenReturn(new Employee(1L, "name", "lastName", 1000L, "role"));

		given().
		when().
			get("/api/employees/1").
		then().	
			statusCode(200).
			assertThat().
			body("id", equalTo(1), 
				 "name", equalTo("name"), 
				 "lastName", equalTo("lastName"), 
				 "salary", equalTo(1000),
				 "role", equalTo("role"));
	}
	
	@Test
	public void testFindEmployeeById_whenNotFound() throws EmployeeNotFoundException {
		when(employeeService.getEmployeeById(anyLong())).
			thenThrow(EmployeeNotFoundException.class);
		
		given().
		when().
			get("/api/employees/1").
		then().	
			statusCode(404).
			statusLine(containsString("Employee Not Found"));
	}
}
