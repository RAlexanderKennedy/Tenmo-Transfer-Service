package com.techelevator.tenmo.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Balance getBalance(int userId) {
		
		String sql = "select balance from accounts where user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		
		Balance userBalance = new Balance();
		
		if (results.next()) {
			userBalance.setBalance(results.getDouble("balance"));
			
		}
		
		return userBalance;
	}

	@Override
	public Balance updateBalance(int userId, Balance balance) {
		
		String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		
		jdbcTemplate.update(sql, balance.getBalance(), userId);
		
		return balance;
	}

	@Override
	public Transfer createNewTransfer(Transfer newTransfer) {
		
		String sql = "INSERT into transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?,?,?,?,?,?)";
		newTransfer.setTransfer_id(getNextTransferId());
		
		jdbcTemplate.update(sql, newTransfer.getTransfer_id(), newTransfer.getTransfer_type_id(), newTransfer.getTransfer_status_id(), newTransfer.getAccount_from(), newTransfer.getAccount_to(), newTransfer.getAmount());
		
		
		return newTransfer;
	}
	

	public int getNextTransferId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new transfer");
		}
	}
	@Override
	public Transfer getTransfersByUserId (int id) {
		Transfer newTransfer = null;
		String sql = "SELECT transfer_id, transfer_type_id,  username, amount FROM transfers JOIN users ON transfers.account_from = users.user_id  WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		while (results.next()) {
			
			newTransfer.setAmount(results.getDouble("amount"));
			newTransfer.setTransfer_type_id(results.getInt("transfer_type_id"));
			newTransfer.setTransfer_id(results.getInt("transfer_id"));
			newTransfer.setUsername(results.getString("username"));
		}
		
		return newTransfer;
	}
	
	public Transfer mapRowToTransfer (SqlRowSet results) {
		
		Transfer newTransfer = new Transfer();
		newTransfer.setAccount_from(results.getInt("account_from"));
		newTransfer.setAccount_to(results.getInt("account_to"));
		newTransfer.setAmount(results.getDouble("amount"));
		newTransfer.setTransfer_type_id(results.getInt("transfer_type_id"));
		newTransfer.setTransfer_status_id(results.getInt("transfer_status_id"));
		newTransfer.setTransfer_id(results.getInt("transfer_id"));
		
		return newTransfer;
	}
	
	

}
