package com.auth.user.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.*;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class RestTemplateConfig {

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		builder.messageConverters(messageConverters);
//		RestTemplate restTemplate=builder.build();
		
//		 List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
//       if (CollectionUtils.isEmpty(interceptors)) {
//           interceptors = new ArrayList<>();
//       }
//       interceptors.add(new RestTemplateHeaderModifierInterceptor());
//       restTemplate.setInterceptors(interceptors);
//       return restTemplate;
		
		return builder.build();
	}
}
