package com.auth.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.auth.user.util.ProductStatus;
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
public class Product {
	private Long id;
	private String name;
	private String category;
	private String sellerName;
	@Builder.Default
	BigDecimal askPrice = new BigDecimal(0);
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime endTime;
	@JsonProperty
	private ProductStatus status;
}

