package com.jc.bank.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jc.bank.exception.InsufficientFundsException;

@Entity
@Table(name = "tb_account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "balance")
	private BigDecimal balance;
	
	
	public Account() {
		super();
	}

	public Account(Long id, String name, BigDecimal balance) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(balance, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(balance, other.balance) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
	
	public void withdraw(BigDecimal amount) {
		BigDecimal newBalance = this.balance.subtract(amount);
		
		if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new InsufficientFundsException("Insufficient Funds! you don't have enough money in your checking account to cover the entire transaction.");
		}
		this.balance = newBalance;
	}
	
	public void deposit(BigDecimal amount) {
		this.balance = balance.add(amount);
	}
}