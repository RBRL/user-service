package com.auth.user.exception;

public class AuctionClientException extends UserServiceException {
	public AuctionClientException(){
		super();
	}
	public AuctionClientException(String message){
		super(message);
	}
	public AuctionClientException(Throwable t){
		super(t.getMessage());
		
	}
	public AuctionClientException(Throwable t,String message){
		
	}
}
