package com.auth.user.client;

import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth.user.dto.Auction;
import com.auth.user.dto.Bid;
import com.auth.user.dto.Product;
import com.auth.user.exception.AuctionClientException;
import com.auth.user.exception.UserServiceException;

@Service
public class AuctionClient {

	@Autowired
	public RestTemplate restTemplate;

	@Value("${auction.service.hostport}")
	public String AUCTION_HOST;

	@Value("${auction.service.baseurl}")
	public String BASE_URL;

	public String getAuctionHome(String token) throws AuctionClientException {
		try {
			String path = "/home";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
			// Use Token to get Response
			ResponseEntity<String> helloResponse = restTemplate.exchange(AUCTION_HOST + BASE_URL + path, HttpMethod.GET,
					jwtEntity, String.class);
			return helloResponse.getBody();
		} catch (Exception e) {
			throw new AuctionClientException(e.getMessage());
		}
	}

	public String addProduct(List<Product> product, String token) throws AuctionClientException {
		try {
			String path = "/product/add";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<List<Product>> requestEntity = new HttpEntity<>(product, headers);

			ResponseEntity<String> reponse = restTemplate.exchange(AUCTION_HOST + BASE_URL + path, HttpMethod.POST,
					requestEntity, String.class);
			return reponse.getBody();
		} catch (Exception e) {
			throw new AuctionClientException(e.getMessage());
		}
	}

	public List<Product> viewProducts(String token) throws AuctionClientException {
		try {
			String path = "/product/all";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

			// Use Token to get Response
			ParameterizedTypeReference<List<Product>> parameter = new ParameterizedTypeReference<List<Product>>() {
			};

			ResponseEntity<List<Product>> response = restTemplate.exchange(AUCTION_HOST + BASE_URL + path,
					HttpMethod.GET, requestEntity, parameter);
			return response.getBody();
		} catch (Exception e) {
			throw new AuctionClientException(e.getMessage());
		}
	}

	public Bid bidOnProduct(Bid bid, String token) throws AuctionClientException {
		try {
			String path = "/bid";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<Bid> requestEntity = new HttpEntity<Bid>(bid, headers);

			// Use Token to get Response
			ParameterizedTypeReference<Bid> parameter = new ParameterizedTypeReference<Bid>() {
			};

			ResponseEntity<Bid> response = restTemplate.exchange(AUCTION_HOST + BASE_URL + path, HttpMethod.POST,
					requestEntity, parameter);
			return response.getBody();
		} catch (Exception ex) {
			throw new AuctionClientException(ex.getMessage());

		}
	}

	public Auction endAuction(Long auction, String token) throws AuctionClientException {
		try {
			String path = "/end/{id}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<Long> requestEntity = new HttpEntity<Long>(auction, headers);

			Map<String, String> queryParamMap = new HashMap<>();
			queryParamMap.put("id", String.valueOf(auction));

			UriComponents builder = UriComponentsBuilder.fromHttpUrl(AUCTION_HOST + BASE_URL).path(path)
					.buildAndExpand(queryParamMap);

			ResponseEntity<Auction> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
					requestEntity, Auction.class);
			return response.getBody();
		} catch (Exception e) {
			throw new AuctionClientException(e.getMessage());
		}
	}
}
