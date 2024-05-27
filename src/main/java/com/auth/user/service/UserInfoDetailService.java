package com.auth.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import com.auth.user.controller.AuthenticationController;
import com.auth.user.dto.AddBalanceRequest;
import com.auth.user.entity.UserInfo;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.exception.UserServiceException;
import com.auth.user.model.UserInfoDetails;
import com.auth.user.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoDetailService implements UserDetailsService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	

	public String addUser(UserInfo userInfo) throws UserServiceException {
		try {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfoRepository.save(userInfo);
			return "User " + userInfo.getFirstName() + " added to system ";
		} catch (Exception ex) {
			log.info("Error while adding user "+ex. getMessage());
			throw new UserServiceException(ex.getMessage());
		}

	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = userInfoRepository.findByUserName(username);
		return userInfo.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found with " + username));
	}

	public UserInfo getUser(Long id) throws UserServiceException, UserNotFoundException {
		try {
			Optional<UserInfo> userInfo = userInfoRepository.findById(id);
			if (userInfo.isPresent()) {
				return userInfo.get();
			} else {
				log.info("Error while viewing user with "+id);
				throw new UserNotFoundException("User Not found with id " + id);
			}
		} catch (Exception e) {
			log.info("Error while viewing user with "+e.getMessage());
			throw new UserServiceException(e.getMessage());
		}

	}

	public String addWalletBalance(AddBalanceRequest addBalanceRequest) throws UserServiceException {
		try {
			Optional<UserInfo> userInfo = userInfoRepository.findById(addBalanceRequest.getId());
			if (userInfo.isPresent()) {
				UserInfo user = userInfo.get();
				BigDecimal updatedAmount = addBalanceRequest.getAmount().add(user.getWalletBalance());
				user.setWalletBalance(updatedAmount);
				userInfoRepository.save(user);
				return "Balance is updated to " + user.getWalletBalance();
			} else {
				throw new UserNotFoundException("User Not found with id " + addBalanceRequest.getId());
			}
		} catch (Exception ex) {
			log.error("Error while adding balance to wallet of user with id "+ ex.getMessage()) ;
			throw new UserServiceException(ex.getMessage());
		}
	}
	
	public String viewWalletBalance(String userName) throws UserServiceException {
		try {
			Optional<UserInfo> userInfo = userInfoRepository.findByUserName(userName);
			if (userInfo.isPresent()) {
				return "Wallet balance is to " + userInfo.get().getWalletBalance()+ " for user: "+userName;
			} else {
				throw new UserServiceException("Error while fetching user wallet balance");
			}
		} catch (Exception ex) {
			log.error("Error while viewing wallet banlance") ;
			throw new UserServiceException(ex.getMessage());
		}
	}

	
}
