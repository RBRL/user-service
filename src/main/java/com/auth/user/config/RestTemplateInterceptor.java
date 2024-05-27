package com.auth.user.config;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateInterceptor{
//implements ClientHttpRequestInterceptor {
//
//  @Override
//  public ClientHttpResponse intercept(
//    HttpRequest request, 
//    byte[] body, 
//    ClientHttpRequestExecution execution) throws IOException {
//
//      ClientHttpResponse response = execution.execute(request, body);
//      response.getHeaders().add("Foo", "bar");
//      return response;
//  }
}