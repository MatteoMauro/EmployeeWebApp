package com.matteomauro.web_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.matteomauro.exception.EmployeeNotFoundException;
import com.matteomauro.model.Employee;
import com.matteomauro.service.EmployeeService;

@Controller
public class EmployeeWebController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/")
	public String index(Model model) {
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("employees", employees);
		model.addAttribute("message", employees.isEmpty() ? "No Employee" : "");
		return "index";
	}

	@GetMapping("/edit/{id}")
	public String editEmployee(@PathVariable Long id, Model model) throws EmployeeNotFoundException {
		try {
			model.addAttribute("employee", employeeService.getEmployeeById(id));
		} catch (EmployeeNotFoundException ex) {
			throw new EmployeeNotFoundException(id);
		}
		return "edit";
	}

	@GetMapping("/new")
	public String newEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "edit";
	}

	@PostMapping("/save")
	public String saveEmployee(Employee employee) throws EmployeeNotFoundException {
		final Long id = employee.getId();
		if (id == null) {
			employeeService.insertNewEmployee(employee);
		} else {
			employeeService.updateEmployeeById(id, employee);
		}
		return "redirect:/";
	}

	@DeleteMapping("/delete/{id}")
	public String saveEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
		try {
			employeeService.deleteById(id);
		} catch (EmployeeNotFoundException ex) {
			throw new EmployeeNotFoundException(id);
		}
		return "redirect:/";
	}

}
