package com.jc.bank.service;

import java.math.BigDecimal;
import java.util.List;

import com.jc.bank.entity.Account;

public interface AccountService {

	List<Account> findAllAccounts();
	Account findAccountById(Long id);
	Account createAccount(Account account);
	Integer wireTransfersNumber(Long bankId);
	BigDecimal checkBalance(Long accountId);
	void transferMoney(Long sourceAccount, Long destinationAccount, BigDecimal amount, Long bankId);
}