package com.techelevator.tenmo.services;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Balance;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

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
	
	public Balance returnBalanceById(int id) {
		Balance balance = new Balance();
		
		balance = restTemplate.exchange(BASE_URL + "/get-balance/" + id, HttpMethod.GET, makeAuthEntity(), Balance.class).getBody();
		
		return balance;
		
	}
	
	public User[] returnUsers() {
		User[] userList = null;
		userList = restTemplate.exchange(BASE_URL + "/get-users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
		return userList;
	}
	
	public Balance updateBalance(Balance balance, int id) {
		
		restTemplate.exchange(BASE_URL + "/update-balance/" + id, HttpMethod.PUT, makeBalanceEntity(balance), Balance.class);
		
		return balance;
	}
	
	public Transfer createTransfer(Transfer transfer) {
		restTemplate.exchange(BASE_URL + "/create-transfer", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
		
		return transfer;
	}
	
	private HttpEntity<Balance> makeBalanceEntity(Balance balance) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity<Balance> entity = new HttpEntity<>(balance, headers);
		    return entity;
		  }
	
	private HttpEntity makeAuthEntity() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(AUTH_TOKEN);
	    HttpEntity entity = new HttpEntity<>(headers);
	    return entity;
	  }
	
	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(AUTH_TOKEN);
	    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
	    return entity;
	  }
	
}
