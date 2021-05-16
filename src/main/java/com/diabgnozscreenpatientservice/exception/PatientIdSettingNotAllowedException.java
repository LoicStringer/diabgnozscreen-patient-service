package com.diabgnozscreenpatientservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="Not allowed to set an Id value.")
public class PatientIdSettingNotAllowedException extends Exception {

	private static final long serialVersionUID = 1L;

	public PatientIdSettingNotAllowedException() {
		super();
	}

	
}
