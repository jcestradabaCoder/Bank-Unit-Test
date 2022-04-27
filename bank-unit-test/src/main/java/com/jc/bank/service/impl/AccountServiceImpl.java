package com.jc.bank.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.bank.entity.Account;
import com.jc.bank.entity.Bank;
import com.jc.bank.repository.AccountRepository;
import com.jc.bank.repository.BankRepository;
import com.jc.bank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	

	@Override
	@Transactional(readOnly = true)
	public List<Account> findAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Account findAccountById(Long id) {
		return accountRepository.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer wireTransfersNumber(Long bankId) {
		Bank bank = bankRepository.findById(bankId).orElseThrow();
		return bank.getWireTransfers();
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal checkBalance(Long accountId) {
		Account account = accountRepository.findById(accountId).orElseThrow();
		return account.getBalance();
	}

	@Override
	@Transactional
	public void transferMoney(Long sourceAccount, Long destinationAccount, BigDecimal amount, Long bankId) {
		
		Account sAccount = accountRepository.findById(sourceAccount).orElseThrow();
		sAccount.withdraw(amount);
		accountRepository.save(sAccount);
		
		Account dAccount = accountRepository.findById(destinationAccount).orElseThrow();
		dAccount.deposit(amount);
		accountRepository.save(dAccount);
		
		Bank bank = bankRepository.findById(bankId).orElseThrow();
		int wireTransfers = bank.getWireTransfers();
		bank.setWireTransfers(++wireTransfers);
		bankRepository.save(bank);
	}
}