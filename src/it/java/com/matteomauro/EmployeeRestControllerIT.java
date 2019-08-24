package com.matteomauro;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.matteomauro.dto.EmployeeDTO;
import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeRestControllerIT {

	@Autowired
	private EmployeeRepository employeeRepository;

	@LocalServerPort
	private int port;

	@Before
	public void setup() {
		RestAssured.port = port;
		employeeRepository.deleteAll();
		employeeRepository.flush();
	}

	@Test
	public void testNewEmployee() throws Exception {
		Response response = 
				given().
					contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
					body(new EmployeeDTO(null, "name", "lastName", 1000L, "role")).
				when().
					post("/api/employees/new");
		
		Employee saved = response.getBody().as(Employee.class);
		assertThat(employeeRepository.findById(saved.getId()).get()).isEqualTo(saved);
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		Employee saved = employeeRepository.save(new Employee(null, "name", "lastName", 1000L, "role"));
		given().
			contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
			body(new EmployeeDTO(null, "modified_name", "modified_lastName", 2000L, "modified_role")).
		when().
			put("/api/employees/update/" + saved.getId()).
		then().
			statusCode(200).
			body(
				"id", equalTo(saved.getId().intValue()), 
				"name", equalTo("modified_name"), 
				"lastName", equalTo("modified_lastName"), 
				"salary", equalTo(2000),
				"role", equalTo("modified_role"));
	}
}