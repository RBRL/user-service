package com.auth.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth.user.dto.Auction;
import com.auth.user.dto.Bid;
import com.auth.user.dto.Product;
import com.auth.user.exception.UserServiceException;
import com.auth.user.model.UserInfoDetails;
import com.auth.user.service.AuctionService;
import com.auth.user.service.UserInfoDetailService;
import com.auth.user.util.JWTUtil;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserInfoDetailService userInfoDetailService;

	@Autowired
	AuctionService auctionServiceImpl;

	@Autowired
	JWTUtil jwtUtil;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/ping")
	public String ping() throws UserServiceException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
		return auctionServiceImpl.getAuctionHome(currentUser.getToken());

	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	public String addProduct(@RequestBody List<Product> products) throws UserServiceException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
		return auctionServiceImpl.addProduct(products, currentUser.getToken());

	}

	@PostMapping("/end/{id}")
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	public Auction endAuction(@PathVariable Long id, String token) throws UserServiceException {
		Auction auct = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
			auct = auctionServiceImpl.endAuction(id, currentUser.getToken());
		return auct;
	}

	@GetMapping("/all")
	public List<Product> getAllProducts() throws UserServiceException {
		List<Product> list = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
			list = auctionServiceImpl.viewProducts(currentUser.getToken());
		return list;
	}
	
	@PostMapping("/bid")
	@PreAuthorize("hasAuthority('ROLE_BUYER')")
	public Bid bidOnProduct(@RequestBody Bid bid) throws UserServiceException {
		    Bid userBid = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
			userBid = auctionServiceImpl.bidOnProduct(bid,currentUser.getToken());
		return userBid;
	}
	
	@PostMapping("/end")
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	public Auction endAuction(@PathVariable Long productId) throws UserServiceException {
		Auction productAuction = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
			productAuction = auctionServiceImpl.endAuction(productId,currentUser.getToken());
			return productAuction;
	}
	
}
