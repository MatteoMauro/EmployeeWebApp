package com.matteomauro.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.matteomauro.exception.EmployeeNotFoundException;

@ControllerAdvice("com.matteomauro.web_controller")
public class WebControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleEmployeeNotFound(Model model, EmployeeNotFoundException exception) {
		model.addAttribute("message", "No employee found with id: " + exception.getIdSearched());
		return "employee404";
	}
}
