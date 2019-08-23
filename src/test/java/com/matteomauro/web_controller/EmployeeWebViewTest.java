package com.matteomauro.web_controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static java.util.Collections.emptyList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
}