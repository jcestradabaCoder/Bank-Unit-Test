package com.jc.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jc.bank.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT a FROM Account a WHERE a.name = ?1")
	public Optional<Account> findByName(String name);
}