package com.auth.user.dto;

import java.time.LocalDateTime;

import com.auth.user.config.LocalDateTimeDeserializer;
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