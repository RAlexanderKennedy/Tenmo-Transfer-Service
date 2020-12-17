package com.techelevator.tenmo.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Balance;
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


	
	

}
