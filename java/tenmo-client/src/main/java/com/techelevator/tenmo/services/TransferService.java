package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Balance;

public class TransferService {

	public static String AUTH_TOKEN = "";
	private final String INVALID_TRANSACTION_MSG = "Invalid transaction. Please try again.";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	public TransferService(String url) {
		BASE_URL = url;
	}
	
	public Balance returnBalance() {
		Balance balance = new Balance();
		
		balance = restTemplate.exchange(BASE_URL + "/get-balance", HttpMethod.GET, makeAuthEntity(), Balance.class).getBody();
		
		return balance;
		
	}
	
	
	//use currentUser.getToken() in some way?
	private HttpEntity makeAuthEntity() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(AUTH_TOKEN);
	    HttpEntity entity = new HttpEntity<>(headers);
	    return entity;
	  }
	
}
