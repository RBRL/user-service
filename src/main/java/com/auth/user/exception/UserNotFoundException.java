package com.auth.user.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(){
		super();
	}
	public UserNotFoundException(String message){
		super(message);
	}
	public UserNotFoundException(Throwable t){
		super(t.getMessage());
		
	}
	public UserNotFoundException(Throwable t,String message){
		super(message,t);
	}
}

