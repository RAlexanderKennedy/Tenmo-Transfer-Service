package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	public Balance getBalance(int userId);
	
	public Balance updateBalance (int userId, Balance balance);

}
