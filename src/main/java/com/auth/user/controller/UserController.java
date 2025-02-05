package com.auth.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.user.dto.AddBalanceRequest;
import com.auth.user.entity.UserInfo;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.exception.UserServiceException;
import com.auth.user.service.UserInfoDetailService;

@RestController
@RequestMapping("/user")
public class UserController {


	@Autowired
	UserInfoDetailService userInfoDetailService;

	@GetMapping("/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")//testing
	public ResponseEntity<UserInfo> getUserInfo(@PathVariable Long id)
			throws UserNotFoundException, UserServiceException {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = userInfoDetailService.getUser(id);
		return ResponseEntity.ok(userInfo);
	}

	@PostMapping("/wallet/addmoney")
	public ResponseEntity<String> addMoney(@RequestBody AddBalanceRequest addBalanceRequest)
			throws UserServiceException {
		String response = null;
		response = userInfoDetailService.addWalletBalance(addBalanceRequest);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/wallet/balance")
	public String viewWalletBalance() throws UserServiceException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		return userInfoDetailService.viewWalletBalance(currentUser.getUsername());
	}

	
}
