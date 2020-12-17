package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
	
	@Autowired
	TransferDAO transferDao;
	
	@Autowired
	UserDAO userDao;
	
	@RequestMapping(path="/get-balance", method=RequestMethod.GET)
	public Balance getBalance(Principal principal) {
		
		System.out.println("The following person is requesting a balance: " + principal.getName());
		
		int id = userDao.findIdByUsername(principal.getName());
		Balance balance = transferDao.getBalance(id);
		
		return balance;
	}

	@RequestMapping(path="/get-users", method = RequestMethod.GET)
	public List<User> getUsers() {
		List<User> userList = userDao.findAll();
		return userList;
	}
	
	@RequestMapping(path="/update-balance/{id}", method = RequestMethod.PUT)
	public Balance subtractBalance (@RequestBody Balance balance, @PathVariable int id) {
		transferDao.updateBalance(id, balance);
		Balance newBalance = transferDao.getBalance(id);
		return newBalance;
	}
	
	@RequestMapping(path="/get-balance/{id}", method=RequestMethod.GET)
	public Balance getRecieversBalance(@PathVariable int id) {
		
		Balance balance = transferDao.getBalance(id);
		
		return balance;
	}
}
