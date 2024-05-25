package com.auth.user.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBalanceRequest {

	private Long id;
	@Builder.Default
	BigDecimal amount = new BigDecimal("0");
}
