package com.matteomauro.web_controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.matteomauro.model.Employee;
import com.matteomauro.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeWebController.class)
public class EmployeeWebViewTest {

	@Autowired
	private WebClient webClient;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void testHomePageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/");
		assertThat(page.getTitleText()).isEqualTo("EmployeeWebApp");
	}

	@Test
	public void testHomePageWithNoEmployees() throws Exception {
		when(employeeService.getAllEmployees()).thenReturn(emptyList());
		HtmlPage page = this.webClient.getPage("/");
		assertThat(page.getBody().getTextContent()).contains("No Employee");
	}

	@Test
	public void testHomePageWithEmployees_shouldShowThemInATable() throws Exception {
		Employee employee1 = new Employee(1L, "name1", "lastName1", 1000L, "role1");
		Employee employee2 = new Employee(2L, "name2", "lastName2", 2000L, "role2");
		when(employeeService.getAllEmployees()).thenReturn(asList(employee1, employee2));
		
		HtmlPage page = this.webClient.getPage("/");
		assertThat(page.getBody().getTextContent()).doesNotContain("No Employee");
		HtmlTable table = page.getHtmlElementById("employee_table");
		assertThat(removeWindowsCR(table.asText())).isEqualTo(
				"ID	Name	LastName	Salary	Role\n" +
				"1	name1	lastName1	1000	role1\n" +
				"2	name2	lastName2	2000	role2"
		);
	}
	
	private String removeWindowsCR(String s) {
		return s.replaceAll("\r", "");
	}
}