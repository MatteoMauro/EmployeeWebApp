package com.matteomauro.exception;

public class EmployeeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Long idSearched;

	public EmployeeNotFoundException(Long idSearched) {
		super();
		this.idSearched = idSearched;
	}

	public Long getIdSearched() {
		return idSearched;
	}

}
