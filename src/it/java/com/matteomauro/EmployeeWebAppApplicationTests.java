package com.matteomauro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("mysql")
@SpringBootTest
public class EmployeeWebAppApplicationTests {

	@Test
	public void contextLoads() {
	}

}
