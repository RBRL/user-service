package com.auth.user.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.auth.user.config.LocalDateTimeDeserializer;
import com.auth.user.util.AuctionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
	private Long id;
	private Product product;
	private String buyerId;
	@Builder.Default
	BigDecimal bidPrice = new BigDecimal(0);
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	private LocalDateTime endTime;
	@JsonProperty
	private AuctionStatus status;
	
}

