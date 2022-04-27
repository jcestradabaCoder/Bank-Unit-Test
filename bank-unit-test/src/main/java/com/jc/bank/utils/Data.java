package com.jc.bank.utils;

import java.math.BigDecimal;
import java.util.Optional;

import com.jc.bank.entity.Account;
import com.jc.bank.entity.Bank;

public class Data {

	public static Optional<Account> createAccount001() {
		return Optional.of(new Account(1L, "Juan", new BigDecimal(1000)));
	}
	
	public static Optional<Account> createAccount002() {
		return Optional.of(new Account(2L, "Carlos", new BigDecimal(2000)));
	}
	
	public static Optional<Bank> createBank() {
		return Optional.of(new Bank(1L, "Bancomer", 0));
	}
}