package com.auth.user.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserInfoDetailService userInfoDetailService;

	@Autowired
	JWTUtil jwtUtil;

	@GetMapping("/home")
	public String welcome() {
		return "Welcome";
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserInfo userInfo) throws UserServiceException {
		return ResponseEntity.ok(userInfoDetailService.addUser(userInfo));
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws UserServiceException {
		String token=null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				ArrayList<String> roles= new ArrayList();
				 authentication.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
				 token= jwtUtil.generateToken(authRequest.getUserName(),roles);
				UserInfoDetails userDetail= (UserInfoDetails) authentication.getPrincipal();
				userDetail.setToken(token);
				
			} else {
				throw new UsernameNotFoundException("Not a valid user!");
			}
		} catch (Exception ex) {
			throw new UserServiceException(ex.getMessage());
		}
		return token;
	}

}
