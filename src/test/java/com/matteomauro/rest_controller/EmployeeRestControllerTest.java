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
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.matteomauro.dto.EmployeeDTO;
import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.exception_handler.RestControllerExceptionHandler;
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
		HandlerExceptionResolver handlerExceptionResolver = initGlobalExceptionHandlerResolvers();
		RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(employeeRestController)
				.setHandlerExceptionResolvers(handlerExceptionResolver));
	}

	/**
	 * Necessary to register the exception handler for these unit tests
	 * 
	 * @return
	 */
	private HandlerExceptionResolver initGlobalExceptionHandlerResolvers() {
		StaticApplicationContext applicationContext = new StaticApplicationContext();
		applicationContext.registerSingleton("exceptionHandler", RestControllerExceptionHandler.class);

		WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
		webMvcConfigurationSupport.setApplicationContext(applicationContext);

		return webMvcConfigurationSupport.handlerExceptionResolver();
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

	@Test
	public void testInsertNewEmployee() {
		Employee toSave = new Employee(null, "name", "lastName", 1000L, "role");
		Employee saved = new Employee(1L, "name", "lastName", 1000L, "role");
		when(employeeService.insertNewEmployee(toSave)).
			thenReturn(saved);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(new EmployeeDTO(toSave)).
		when().
			post("/api/employees/new").
		then().	
			statusCode(200).
			body("id", equalTo(1), 
				 "name", equalTo("name"), 
				 "lastName", equalTo("lastName"), 
				 "salary", equalTo(1000),
				 "role", equalTo("role"));
	}
	
	@Test
	public void testUpdateEmployeeById() throws EmployeeNotFoundException {
		Employee replacement = new Employee(null, "newName", "newLastName", 2000L, "newRole");
		when(employeeService.updateEmployeeById(1L, replacement)).
			thenReturn(new Employee(1L, "newName", "newLastName", 2000L, "newRole"));
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(replacement).
		when().
			put("/api/employees/update/1").
		then().
			statusCode(200).
			body("id", equalTo(1), 
				 "name", equalTo("newName"), 
				 "lastName", equalTo("newLastName"), 
				 "salary", equalTo(2000),
				 "role", equalTo("newRole"));
	}
	
	@Test
	public void testUpdateNameEmployeeById() throws EmployeeNotFoundException {
		String newName = "newName";
		Employee modified = new Employee(1L, "newName", "lastName", 1000L, "role");
		when(employeeService.updateEmployeeNameById(1L, newName)).
			thenReturn(modified);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newName).
		when().
			patch("/api/employees/update/name/1").
		then().
			statusCode(200).
			body("id", equalTo(1), 
				"name", equalTo("newName"), 
				"lastName", equalTo("lastName"), 
				"salary", equalTo(1000),
				"role", equalTo("role"));
	}
	
	@Test
	public void testUpdateNameEmployeeById_withIdNotFound_shouldThrow() throws EmployeeNotFoundException {
		String newName = "newName";
		when(employeeService.updateEmployeeNameById(1L, newName)).
			thenThrow(EmployeeNotFoundException.class);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newName).
		when().
			patch("/api/employees/update/name/1").
		then().
			statusCode(HttpStatus.NOT_FOUND.value()).
			statusLine(containsString("Employee Not Found"));
	}
	
	@Test
	public void testUpdateLastNameEmployeeById() throws EmployeeNotFoundException {
		String newLastName = "newLastName";
		Employee modified = new Employee(1L, "name", "newLastName", 1000L, "role");
		when(employeeService.updateEmployeeLastNameById(1L, newLastName)).
			thenReturn(modified);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newLastName).
		when().
			patch("/api/employees/update/lastName/1").
		then().
			statusCode(200).
			body("id", equalTo(1), 
				 "name", equalTo("name"), 
				 "lastName", equalTo("newLastName"), 
				 "salary", equalTo(1000),
				 "role", equalTo("role"));
	}
	
	@Test
	public void testUpdateLastNameEmployeeById_withIdNotFound_shouldThrow() throws EmployeeNotFoundException {
		String newLastName = "newLastName";
		when(employeeService.updateEmployeeLastNameById(1L, newLastName)).
			thenThrow(EmployeeNotFoundException.class);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newLastName).
		when().
			patch("/api/employees/update/lastName/1").
		then().
			statusCode(HttpStatus.NOT_FOUND.value()).
			statusLine(containsString("Employee Not Found"));
	}
	
	@Test
	public void testUpdateSalaryEmployeeById() throws EmployeeNotFoundException {
		Long newSalary = 2000L;
		Employee modified = new Employee(1L, "name", "lastName", 2000L, "role");
		when(employeeService.updateEmployeeSalaryById(1L, newSalary)).
			thenReturn(modified);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newSalary).
		when().
			patch("/api/employees/update/salary/1").
		then().
			statusCode(200).
			body("id", equalTo(1), 
			 	 "name", equalTo("name"), 
		 		 "lastName", equalTo("lastName"), 
				 "salary", equalTo(2000),
				 "role", equalTo("role"));
	}
	
	@Test
	public void testUpdateSalaryEmployeeById_withIdNotFound_shouldThrow() throws EmployeeNotFoundException {
		Long newSalary = 2000L;
		when(employeeService.updateEmployeeSalaryById(1L, newSalary)).
			thenThrow(EmployeeNotFoundException.class);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newSalary).
		when().
			patch("/api/employees/update/salary/1").
		then().
			statusCode(HttpStatus.NOT_FOUND.value()).
			statusLine(containsString("Employee Not Found"));
	}
	
	@Test
	public void testUpdateRoleEmployeeById() throws EmployeeNotFoundException {
		String newRole = "newRole";
		Employee modified = new Employee(1L, "name", "lastName", 1000L, "newRole");
		when(employeeService.updateEmployeeRoleById(1L, newRole)).
			thenReturn(modified);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newRole).
		when().
			patch("/api/employees/update/role/1").
		then().
			statusCode(200).
			body("id", equalTo(1), 
				 "name", equalTo("name"), 
				 "lastName", equalTo("lastName"), 
				 "salary", equalTo(1000),
				 "role", equalTo("newRole"));
	}
	
	@Test
	public void testUpdateRoleEmployeeById_withIdNotFound_shouldThrow() throws EmployeeNotFoundException {
		String newRole = "newRole";
		when(employeeService.updateEmployeeRoleById(1L, newRole)).
			thenThrow(EmployeeNotFoundException.class);
		
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			accept(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(newRole).
		when().
			patch("/api/employees/update/role/1").
		then().
			statusCode(HttpStatus.NOT_FOUND.value()).
			statusLine(containsString("Employee Not Found"));
	}
	
}
