package com.matteomauro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EmployeeWebControllerE2E {
	
	private static int port = Integer.parseInt(System.getProperty("server.port", "8080"));
	private static String baseUrl = "http://localhost:" + port;
	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@Before
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new ChromeDriver();
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void testCreateNewEmployee() {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("a[href*='/new")).click();
		driver.findElement(By.name("name")).sendKeys("new employee");
		driver.findElement(By.name("salary")).sendKeys("2000");
		driver.findElement(By.name("btn_submit")).click();
		
		assertThat(driver.findElement(By.id("employee_table")).getText()).contains("new employee", "2000");
	}
}