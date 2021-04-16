package com.diabgnozscreenpatientservice.exception;

import java.time.LocalDateTime;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandlerCenter  extends ResponseEntityExceptionHandler{

	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotAllowedIdSettingException(PatientNotFoundException ex) {
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex) {

		ExceptionResponse exceptionResponse =  new ExceptionResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				ex.getCause().toString(), ex.getMessage());
		
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
