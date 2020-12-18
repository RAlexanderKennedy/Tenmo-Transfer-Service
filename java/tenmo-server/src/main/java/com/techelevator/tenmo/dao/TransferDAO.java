package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	public Balance getBalance(int userId);
	
	public Balance updateBalance (int userId, Balance balance);
	
	public Transfer createNewTransfer (Transfer newTransfer);
	
	public Transfer getTransfersByUserId (int id);


}
