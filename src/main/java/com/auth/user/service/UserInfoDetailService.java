package com.auth.user.service;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.auth.user.dto.AddBalanceRequest;
import com.auth.user.entity.UserInfo;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.exception.UserServiceException;
import com.auth.user.model.UserInfoDetails;
import com.auth.user.repository.UserInfoRepository;

@Service
public class UserInfoDetailService implements UserDetailsService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	 
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	public String addUser(UserInfo userInfo) throws UserServiceException {
		try {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfoRepository.save(userInfo);
			return "User " + userInfo.getFirstName() + " added to system ";
		} catch (Exception ex) {
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
				throw new UserNotFoundException("User Not found with id " + id);
			}
		} catch (Exception e) {
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
			throw new UserServiceException(ex.getMessage());
		}
	}

	public String getAuctionHome(String token) {
		String url = "http://localhost:8081/auction/home";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jwtToken = "Bearer " + token;
		headers.set("Authorization", jwtToken);
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		// Use Token to get Response
		ResponseEntity<String> helloResponse = restTemplate().exchange(url, HttpMethod.GET, jwtEntity,
				String.class);
		return helloResponse.getBody();
	}

}
