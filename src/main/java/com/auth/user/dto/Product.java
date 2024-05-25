package com.auth.user.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.auth.user.util.ProductStatus;

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
	private String description;
	private String category;
	@Builder.Default
	private BigDecimal askPrice=new BigDecimal("0");
	private Date timeCreated;
	private ProductStatus status;
		
	
}
