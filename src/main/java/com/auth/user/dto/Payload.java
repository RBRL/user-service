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
public class Payload {
		@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	    private LocalDateTime  dateTime;
	}