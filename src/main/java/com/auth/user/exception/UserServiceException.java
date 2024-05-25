package com.auth.user.exception;

public class UserServiceException extends Exception {
	public UserServiceException(){
		super();
	}
	public UserServiceException(String message){
		super(message);
	}
	public UserServiceException(Throwable t){
		super(t.getMessage());
		
	}
	public UserServiceException(Throwable t,String message){
		super(message,t);
	}
}
