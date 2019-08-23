package com.matteomauro.web_controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.gargoylesoftware.htmlunit.WebAssert.assertTitleEquals;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeWebController.class)
public class EmployeeWebViewTest {

	@Autowired
	private WebClient webClient;

	@MockBean
	private EmployeeService employeeService;

	@Before
	/**
	 * Necessary to restore failing test when exceptions occur
	 */
	public void clearSessionOfWebClient() {
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
	}
	
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
	
	@Test
	public void testEditNonExistentEmployee() throws Exception {
		when(employeeService.getEmployeeById(1L)).thenThrow(EmployeeNotFoundException.class);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = this.webClient.getPage("/edit/1");
		assertTitleEquals(page, "404 Employee Not Found");
		assertThat(page.getBody().getTextContent())
			.contains("No employee found with id: 1");
	}
	
	@Test
	public void testEditExistentEmployee() throws Exception {
		when(employeeService.getEmployeeById(1L))
			.thenReturn(new Employee(1L, "test_name", "test_lastName", 1000L, "test_role"));
		
		HtmlPage page = this.webClient.getPage("/edit/1");
		final HtmlForm form = page.getFormByName("employee_form");
		form.getInputByValue("test_name").setValueAttribute("modified_name");
		form.getInputByValue("test_lastName").setValueAttribute("modified_lastName");
		form.getInputByValue("1000").setValueAttribute("2000");
		form.getInputByValue("test_role").setValueAttribute("modified_role");
		form.getButtonByName("btn_submit").click();
		verify(employeeService)
			.updateEmployeeById(1L, new Employee(1L, "modified_name", "modified_lastName", 2000L, "modified_role"));
	}
	
	@Test
	public void testEditNewEmployee() throws Exception {
		HtmlPage page = this.webClient.getPage("/new");
		final HtmlForm form = page.getFormByName("employee_form");
		form.getInputByName("name").setValueAttribute("new_name");
		form.getInputByName("lastName").setValueAttribute("new_lastName");
		form.getInputByName("salary").setValueAttribute("1000");
		form.getInputByName("role").setValueAttribute("new_role");
		form.getButtonByName("btn_submit").click();
		verify(employeeService)
			.insertNewEmployee(new Employee(null, "new_name", "new_lastName", 1000L, "new_role"));
	}
	
	private String removeWindowsCR(String s) {
		return s.replaceAll("\r", "");
	}
}