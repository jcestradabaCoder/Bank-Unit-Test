package com.jc.bank.dto;

import java.math.BigDecimal;

public class TransactionDTO {

	private Long sourceAccount;
	private Long destinationAccount;
	private BigDecimal amount;
	private Long bankId;
	
	
	public TransactionDTO() {
		super();
	}


	public Long getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(Long sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public Long getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(Long destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
}