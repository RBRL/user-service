package com.auth.user.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.user.model.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
@RestControllerAdvice
public class GlobalUserExceptionHandler {
	
	@ExceptionHandler(value=UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> noRuleFoundException(UserNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		
	}
	
	@ExceptionHandler(value=UserServiceException.class)
	public ResponseEntity<Object> handleUserNotFoundException(
			UserServiceException ex) {
		    Map<String, Object> body = new HashMap<>();
		    body.put("message", ex.getMessage());
		    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	
	 @ExceptionHandler(value = Exception.class)
	    public ResponseEntity<Object> runtimeException(Exception exception) {
	        ErrorResponse errorResponse =null;
	        if (exception instanceof AccessDeniedException) {
	        	errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
	        }

	        if (exception instanceof SignatureException) {
	        	errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
	        }

	        if (exception instanceof ExpiredJwtException) {
	        	errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "The JWT token has expired");
	        }

	        if (errorResponse == null) {
	        	 errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
	        }
			 return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	    }
}
