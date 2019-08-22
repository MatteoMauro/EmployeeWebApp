package com.matteomauro.web_controller;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.service.EmployeeService;
import com.matteomauro.web_controller.EmployeeWebController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeWebController.class)
public class EmployeeWebControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmployeeService employeeService;

	@Test
	public void testStatus200() throws Exception {
		mvc.perform(get("/")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void testHomepage_showsEmployees() throws Exception {
		List<Employee> employees = asList(new Employee(1L, "name", "lastName", 1000L, "role"));
		when(employeeService.getAllEmployees()).thenReturn(employees);
		
		mvc.perform(get("/")).
			andExpect(view().name("index")).
			andExpect(model().attribute("employees", employees)).
			andExpect(model().attribute("message", ""));
	}
	
	@Test
	public void test_HomeView_ShowsMessageWhenThereAreNoEmployees() throws Exception {
		when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());
		
		mvc.perform(get("/")).
			andExpect(view().name("index")).
			andExpect(model().attribute("employees", Collections.emptyList())).
			andExpect(model().attribute("message", "No Employee"));
	}
	
	public void testEditEmployee_whenEmployeeIsFound() throws Exception {
		Employee employee = new Employee(1L, "name", "lastName", 1000L, "role");
		when(employeeService.getEmployeeById(1L)).thenReturn(employee);
		
		mvc.perform(get("/edit/1")).
			andExpect(view().name("edit")).
			andExpect(model().attribute("employee", employee));
	}

	@Test
	public void test_EditEmployee_WhenEmployeeIsNotFound() throws Exception {
		when(employeeService.getEmployeeById(1L)).thenThrow(EmployeeNotFoundException.class);
	
		mvc.perform(get("/edit/1")).
			andExpect(view().name("employee404")).
			andExpect(model().attribute("employee", nullValue())).
			andExpect(model().attribute("message", "No employee found with id: 1"));
	}

	@Test
	public void testNewEmployee() throws Exception {
		mvc.perform(get("/new")).
			andExpect(view().name("edit")).
			andExpect(model().attribute("employee", new Employee()));
		verifyZeroInteractions(employeeService);
	}
	
	
	
	
	
}