package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> globalExceptionHandler
	(Exception ex, WebRequest webRequest){
		
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setError(ex.getMessage());
		errors.setTimestamp(LocalDateTime.now());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

}
