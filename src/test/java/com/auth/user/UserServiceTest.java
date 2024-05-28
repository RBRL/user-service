package com.auth.user;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.auth.user.dto.AuthRequest;
import com.auth.user.entity.UserInfo;
import com.auth.user.exception.UserServiceException;
import com.auth.user.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes=UserServiceApplication.class)
@AutoConfigureMockMvc
public class UserServiceTest {

	@Autowired
    private MockMvc mvc;
    
    @Autowired
    JWTUtil jwtUtil;
    
    
    @Test
    public void registerUserTest() throws Exception {
    	    	ObjectMapper objectMapper = new ObjectMapper();
    	        UserInfo userDetails = UserInfo.builder()
    	                .firstName("Test")
    	                .lastName("K")
    	                .userName("test1")
    	                .walletBalance(new BigDecimal("100.00"))
    	                .roles("ROLE_USER")
    	                .email("jerry.k@gmail.com").password("test")
    	                .build();

    	                //should return Ok if user is added
    	        		mvc.perform(post("/auth/register")
    	                .contentType("application/json")
    	                .content(objectMapper.writeValueAsString(userDetails)))
    	                .andExpect(status().isOk());


    }

    @Test
    public void shouldAllowAccessToUnsecuredAPITest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/auth/home")).andExpect(status().isOk());
    }

    @Test
    public void shouldGenerateAuthTokenTest() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        UserInfo userDetails = UserInfo.builder()
                .firstName("Jerry")
                .lastName("K")
                .userName("user1")
                .walletBalance(new BigDecimal("100.00"))
                .roles("USER")
                .email("jerry.k@gmail.com").password("test")
                .build();

        MvcResult mvcResult = mvc.perform(post("/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        ArrayList<String> roles = new ArrayList();
        roles.add("ROLE_USER");
        String token = jwtUtil.generateToken("user1",roles);
        assertNotNull(token);
    }
    
    
    @Test
    public void shouldCallAPIWithAuthTokenTest() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        UserInfo userDetails = UserInfo.builder()
                .firstName("John")
                .lastName("K")
                .userName("user2")
                .walletBalance(new BigDecimal("100.00"))
                .roles("USER")
                .email("john.k@gmail.com").password("test")
                .build();

        MvcResult mvcResult = mvc.perform(post("/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isOk()
                ).andReturn();

        ArrayList<String> roles = new ArrayList();
        roles.add("ROLE_USER");
        String token = jwtUtil.generateToken("user2",roles);
        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("/user/wallet/balance").header("Authorization", "Bearer "+token)).andExpect(status().isOk());
        String balanceResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(balanceResponseBody,"User John added to system ");
    }
    
    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsersTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/1")).andExpect(status().isForbidden());
    }
    
    @Test
    public void nonexistentUserCannotGetJWTTokenTest() throws Exception {
        String username = "nonexistentuser";
        String password = "password";
        AuthRequest authReq= AuthRequest.builder().userName(username).password(password).build();
        ObjectMapper objectMapper = new ObjectMapper();
       
        MvcResult mvcResult = mvc.perform(post("/auth/authenticate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authReq)))
                .andExpect(status().isBadRequest()
                ).andReturn();
        

        mvcResult.getResolvedException().getMessage();
        Optional<UserServiceException> someException = Optional.ofNullable((UserServiceException) mvcResult.getResolvedException());
        someException.ifPresent( (se) -> assertThat(se, is(notNullValue())));
        someException.ifPresent( (se) -> assertThat(se, is(instanceOf(UserServiceException.class))));
       
    }
    

}