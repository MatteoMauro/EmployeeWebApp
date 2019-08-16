package com.matteomauro.rest_controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
			assertThat().
				body(is(equalTo("[]")));
	}
}
