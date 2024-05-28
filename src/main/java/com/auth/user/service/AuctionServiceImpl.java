package com.auth.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.user.client.AuctionClient;
import com.auth.user.dto.Auction;
import com.auth.user.dto.Bid;
import com.auth.user.dto.Product;
import com.auth.user.exception.UserServiceException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {
	
	
	@Autowired
	AuctionClient auctionClient;

	@Override
	public String getAuctionHome(String token) throws UserServiceException {
		log.info("In Auction  service");
		return auctionClient.getAuctionHome(token);
	}

	@Override
	public String addProduct(List<Product> product, String token) throws UserServiceException {
		return auctionClient.addProduct(product, token);
	}

	@Override
	public List<Product> viewProducts(String token) throws  UserServiceException {
		List<Product> list = auctionClient.viewProducts(token);
		return list;
	}

	@Override
	public Bid bidOnProduct(Bid bid, String token) throws UserServiceException {
		try {
			Bid bidAdded = auctionClient.bidOnProduct(bid, token);
			return bidAdded;
		} catch (Exception ex) {
			log.error("Error while placing a bid");
			throw new UserServiceException(ex.getMessage());
		}

	}

	@Override
	public Auction endAuction(Long auctionId, String token) throws UserServiceException {
		Auction auct = auctionClient.endAuction(auctionId, token);
		return auct;
	}

}
