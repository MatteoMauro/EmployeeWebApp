package com.matteomauro.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.repository.EmployeeRepository;
import com.matteomauro.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	public void testGetAllEmployees() {
		Employee employee1 = new Employee(1L, "name1", "lastName1", 1000L, "role1");
		Employee employee2 = new Employee(2L, "name2", "lastName2", 2000L, "role2");
		when(employeeRepository.findAll()).thenReturn(asList(employee1, employee2));
		assertThat(employeeService.getAllEmployees()).containsExactly(employee1, employee2);
	}

	@Test
	public void testGetEmployeeById_whenIsPresent() throws Exception {
		Employee employee = new Employee(1L, "name", "lastName", 1000L, "role");
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		assertThat(employeeService.getEmployeeById(1L)).isEqualTo(employee);
	}

	@Test
	public void testGetEmployeeById_whenIsNotPresent_shouldThrow() throws Exception {
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeService.getEmployeeById(1L))
				.withMessage("Employee Not Found");
	}

	@Test
	public void testInsertNewEmployee_shouldSetIdToNullAndReturnSavedEmployee() {
		Employee employeeToSave = spy(new Employee(null, "name", "lastName", 1000L, "role"));
		Employee employeeSaved = new Employee(1L, "name", "lastName", 1000L, "role");

		when(employeeRepository.save(employeeToSave)).thenReturn(employeeSaved);
		Employee result = employeeService.insertNewEmployee(employeeToSave);

		assertThat(result).isEqualTo(employeeSaved);
		InOrder inOrder = inOrder(employeeToSave, employeeRepository);
		inOrder.verify(employeeToSave).setId(null);
		inOrder.verify(employeeRepository).save(employeeToSave);
	}

	@Test
	public void testInsertNewEmployee_whenEmployeePassedIsNull_shouldThrowException() {
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> employeeService.insertNewEmployee(null)).withMessage("Employee must not be null");
		verifyNoMoreInteractions(employeeRepository);
	}

	@Test
	public void testUpdateEmployee_shouldSetIdToNullAndReturnSavedEmployee() throws Exception {
		Employee replacement = spy(new Employee(null, "name", "lastName", 1000L, "role"));
		Employee replaced = new Employee(1L, "name", "lastName", 1000L, "role");

		when(employeeRepository.save(any(Employee.class))).thenReturn(replaced);
		Employee result = employeeService.updateEmployeeById(1L, replacement);

		assertThat(result).isSameAs(replaced);
		InOrder inOrder = inOrder(replacement, employeeRepository);
		inOrder.verify(replacement).setId(1L);
		inOrder.verify(employeeRepository).save(replacement);
	}

	@Test
	public void testUpdateEmployee_whenEmployeePassedIsNull_shouldThrow() throws Exception {
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> employeeService.updateEmployeeById(1L, null))
				.withMessage("Employee must not be null");
		verifyNoMoreInteractions(employeeRepository);
	}

	@Test
	public void testUpdateEmployee_whenIdPassedIsNull_shouldThrow() throws Exception {
		Employee replacement = new Employee(null, "name", "lastName", 1000L, "role");
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> employeeService.updateEmployeeById(null, replacement))
				.withMessage("Id must not be null");
		verifyNoMoreInteractions(employeeRepository);
	}

	@Test
	public void testUpdateEmployee_whenIdPassedIsLessOrEqualZero_shouldThrow() throws Exception {
		Employee replacement = new Employee(null, "name", "lastName", 1000L, "role");
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> employeeService.updateEmployeeById(null, replacement))
				.withMessage("Id must not be less or equal to zero");
		verifyNoMoreInteractions(employeeRepository);
	}

}
