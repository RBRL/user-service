package com.auth.user.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuctionClient {

	@Autowired
	public RestTemplate restTemplate;

	@Value("${auction.service.hostport}")
	public String AUCTION_HOST;

	@Value("${auction.service.baseurl}")
	public String BASE_URL;
	
	public static final  String AUCTION_HOME = "/home";
	public static final  String ADD_PRODUCTS = "/products/add";
	public static final  String VIEW_ALL = "/products/all";
	public static final  String BID_ONPRODUCT = "/bid";
	public static final  String END_AUCTION  = "/end/{id}";;
	//TODO move client rest template creation and setting token to common place
	public String getAuctionHome(String token) throws AuctionClientException {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
			// Use Token to get Response
			ResponseEntity<String> helloResponse = restTemplate.exchange(AUCTION_HOST + BASE_URL + AUCTION_HOME, HttpMethod.GET,
					jwtEntity, String.class);
			return helloResponse.getBody();
		} catch (Exception e) {
			log.error("Auction service ping error");
			throw new AuctionClientException(e.getMessage());
		}
	}

	public String addProduct(List<Product> product, String token) throws AuctionClientException {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<List<Product>> requestEntity = new HttpEntity<>(product, headers);
			// Use Token to get Response
			ResponseEntity<String> reponse = restTemplate.exchange(AUCTION_HOST + BASE_URL + ADD_PRODUCTS, HttpMethod.POST,
					requestEntity, String.class);
			return reponse.getBody();
		} catch (Exception e) {
			log.error("Cannot save Products");
			throw new AuctionClientException(e.getMessage());
		}
	}

	public List<Product> viewProducts(String token) throws AuctionClientException {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

			
			ParameterizedTypeReference<List<Product>> parameter = new ParameterizedTypeReference<List<Product>>() {
			};
			// Use Token to get Response
			ResponseEntity<List<Product>> response = restTemplate.exchange(AUCTION_HOST + BASE_URL + VIEW_ALL,
					HttpMethod.GET, requestEntity, parameter);
			return response.getBody();
		} catch (Exception e) {
			log.error("Cannot view all Products");
			throw new AuctionClientException(e.getMessage());
		}
	}

	public Bid bidOnProduct(Bid bid, String token) throws AuctionClientException {
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<Bid> requestEntity = new HttpEntity<Bid>(bid, headers);

			
			ParameterizedTypeReference<Bid> parameter = new ParameterizedTypeReference<Bid>() {
			};
			// Use Token to get Response
			ResponseEntity<Bid> response = restTemplate.exchange(AUCTION_HOST + BASE_URL + BID_ONPRODUCT, HttpMethod.POST,
					requestEntity, parameter);
			return response.getBody();
		} catch (Exception ex) {
			log.error("Error in Auction client biddong on product");
			throw new AuctionClientException(ex.getMessage());

		}
	}

	public Auction endAuction(Long auction, String token) throws AuctionClientException {
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String jwtToken = "Bearer " + token;
			headers.set("Authorization", jwtToken);
			// set request body
			HttpEntity<Long> requestEntity = new HttpEntity<Long>(auction, headers);

			Map<String, String> queryParamMap = new HashMap<>();
			queryParamMap.put("id", String.valueOf(auction));

			UriComponents builder = UriComponentsBuilder.fromHttpUrl(AUCTION_HOST + BASE_URL).path(END_AUCTION)
					.buildAndExpand(queryParamMap);

			ResponseEntity<Auction> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
					requestEntity, Auction.class);
			return response.getBody();
		} catch (Exception e) {
			log.error("Error in Auction client ending an aution");
			throw new AuctionClientException(e.getMessage());
		}
	}
}
