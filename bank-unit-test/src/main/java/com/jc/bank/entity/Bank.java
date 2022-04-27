package com.jc.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_bank")
public class Bank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "wireTransfers")
	private Integer wireTransfers;
	
	
	public Bank() {
		super();
	}

	public Bank(Long id, String name, Integer wireTransfers) {
		super();
		this.id = id;
		this.name = name;
		this.wireTransfers = wireTransfers;
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

	public Integer getWireTransfers() {
		return wireTransfers;
	}

	public void setWireTransfers(Integer wireTransfers) {
		this.wireTransfers = wireTransfers;
	}
}