package com.diabgnozscreenpatientservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="This patient is not registered.")
public class PatientNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	
	
	public PatientNotFoundException() {
		super();
	}



	public PatientNotFoundException(String message) {
		super(message);
	}

	

}
