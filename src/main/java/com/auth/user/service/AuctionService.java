package com.auth.user.service;

import java.util.List;

import com.auth.user.dto.Product;
import com.auth.user.exception.UserServiceException;
import com.auth.user.dto.Auction;
import com.auth.user.dto.Bid;

public interface AuctionService {

	String getAuctionHome(String token) throws UserServiceException;

	String addProduct(List<Product> product, String token) throws UserServiceException;
	
	public List<Product> viewProducts(String token) throws UserServiceException;

	public Bid bidOnProduct(Bid bid,String token) throws UserServiceException;

	public Auction endAuction(Long auction,String token) throws UserServiceException;

}
