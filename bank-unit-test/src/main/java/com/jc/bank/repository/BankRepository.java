package com.jc.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jc.bank.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}