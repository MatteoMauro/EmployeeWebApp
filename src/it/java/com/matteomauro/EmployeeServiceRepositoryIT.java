package com.matteomauro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;
import com.matteomauro.service.EmployeeService;
import com.matteomauro.service.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(EmployeeServiceImpl.class)
public class EmployeeServiceRepositoryIT {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void testServiceCanInsertIntoRepository() {
		Employee saved = employeeService.insertNewEmployee(new Employee(null, "name", "lastName", 1000L, "role"));
		employeeRepository.findById(saved.getId()).isPresent();
	}

	@Test
	public void testServiceCanUpdateIntoRepository() throws EmployeeNotFoundException {
		Employee saved = employeeRepository.save(new Employee(null, "name", "lastName", 1000L, "role"));
		Employee modified = employeeService.updateEmployeeById(saved.getId(),
				new Employee(null, "new_name", "new_lastName", 2000L, "new_role"));
		assertThat(employeeRepository.findById(saved.getId()).get()).isEqualTo(modified);
	}
}
