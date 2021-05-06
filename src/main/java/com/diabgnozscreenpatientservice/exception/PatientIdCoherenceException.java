package com.diabgnozscreenpatientservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="Patient to update is different from updated patient.")
public class PatientIdCoherenceException extends Exception{

	private static final long serialVersionUID = 1L;

	public PatientIdCoherenceException() {
		super();
	}

	public PatientIdCoherenceException(String message) {
		super(message);
	}

	
	
}
