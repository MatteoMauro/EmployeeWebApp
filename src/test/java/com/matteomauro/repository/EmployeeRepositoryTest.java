package com.matteomauro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.matteomauro.model.Employee;

@DataJpaTest
@RunWith(SpringRunner.class)
public class EmployeeRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testJpaMapping() {
		Employee employee = new Employee(null, "name", "lastName", 1000L, "role");
		Employee saved = new Employee(1L, "name", "lastName", 1000L, "role");
		Employee result = entityManager.persistFlushFind(employee);
		assertThat(result).isEqualTo(saved);
	}

}
