package com.auth.user.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.user.dto.AddBalanceRequest;
import com.auth.user.dto.AuthRequest;
import com.auth.user.entity.UserInfo;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.exception.UserServiceException;
import com.auth.user.model.UserInfoDetails;
import com.auth.user.service.UserInfoDetailService;
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
	JWTUtil jwtUtil;

	

	@GetMapping("/auction/home")
	public String getAuctionHome() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfoDetails currentUser = (UserInfoDetails) authentication.getPrincipal();
		return userInfoDetailService.getAuctionHome(currentUser.getToken());
		
	}
	
}
