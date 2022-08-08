package com.java.rest.webservices.restfulwebservices.exception.handler;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java.rest.webservices.restfulwebservices.exception.StudentNotFoundException;
import com.java.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.java.rest.webservices.restfulwebservices.exception.response.ExceptionResponse;

@ControllerAdvice
@RestController
public class CustomizedReponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ UserNotFoundException.class, StudentNotFoundException.class })
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
				ex.getBindingResult().toString());

		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
