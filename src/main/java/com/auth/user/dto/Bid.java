package com.auth.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.auth.user.util.AuctionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
	private Long id;
	private Product product;
	private Long prodId;
	private String buyerId;
	@Builder.Default
	BigDecimal biddingPrice = new BigDecimal(0);
	@JsonProperty
	private AuctionStatus status;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime bidTime;
	@Builder.Default
	private List<Bid> bids = new ArrayList<Bid>();
}
