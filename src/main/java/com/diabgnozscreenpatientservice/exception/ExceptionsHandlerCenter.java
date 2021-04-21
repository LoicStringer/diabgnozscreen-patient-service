package com.diabgnozscreenpatientservice.exception;

import java.time.LocalDateTime;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandlerCenter  extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseStatusException handleException(Exception ex) {
		return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				"An unpredicted exception occured.",ex);
	}
	
	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotAllowedIdSettingException(PatientNotFoundException ex) {
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	private ExceptionResponse exceptionResponseBuild(Exception ex) {
		String statusCode = getStatusCodeFromException(ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode,
				ex.getClass().getSimpleName(), ex.getMessage());
		return exceptionResponse;
	}

	private HttpStatus getHttpStatusFromException(Exception ex) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.value();
		return status;
	}

	private String getStatusCodeFromException(Exception ex) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.code();
		return status.toString();
	}
	
	private String getReasonFromResponseStatus(Exception ex) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		return responseStatus.reason();
	}
	
}
