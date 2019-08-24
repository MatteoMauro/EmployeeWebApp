package com.matteomauro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeWebControllerIT {

	@Autowired
	private EmployeeRepository employeeRepository;

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private String baseUrl;

	@Before
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new HtmlUnitDriver();
		employeeRepository.deleteAll();
		employeeRepository.flush();
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void testHomePage() {
		Employee testEmployee = employeeRepository.save(new Employee(null, "name", "lastName", 1000L, "role"));
		driver.get(baseUrl);

		assertThat(driver.findElement(By.id("employee_table")).getText()).contains("name", "lastName", "1000", "role",
				"Edit");
		driver.findElement(By.cssSelector("a[href*='/edit/" + testEmployee.getId() + "']"));
	}

	@Test
	public void testEditPageNewEmployee() throws Exception {
		driver.get(baseUrl + "/new");
		driver.findElement(By.name("name")).sendKeys("new employee");
		driver.findElement(By.name("salary")).sendKeys("2000");
		driver.findElement(By.name("btn_submit")).click();
		assertThat(employeeRepository.findAll().get(0).getSalary()).isEqualTo(2000L);
	}

	@Test
	public void testEditPageUpdateEmployee() throws Exception {
		Employee testEmployee = employeeRepository.save(new Employee(null, "name", "lastName", 1000L, "role"));
		driver.get(baseUrl + "/edit/" + testEmployee.getId());
		final WebElement nameField = driver.findElement(By.name("name"));
		nameField.clear();
		nameField.sendKeys("modified_name");
		driver.findElement(By.name("btn_submit")).click();
		assertThat(employeeRepository.findAll().get(0).getName()).isEqualTo("modified_name");
	}
}
