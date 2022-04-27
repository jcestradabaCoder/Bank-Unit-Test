package com.jc.bank.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jc.bank.dto.TransactionDTO;
import com.jc.bank.entity.Account;
import com.jc.bank.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/accounts")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Account> getAllAccounts() {
		return accountService.findAllAccounts();
	}
	
	@GetMapping("/accounts/{accountId}")
	@ResponseStatus(code = HttpStatus.OK)
	public Account getDetails(@PathVariable(name = "accountId") Long accountId) {
		return accountService.findAccountById(accountId);
	}
	
	@PostMapping("/accounts")
	@ResponseStatus(code = HttpStatus.OK)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}
	
	@PostMapping("/accounts/transference")
	public ResponseEntity<?> transferMoney(@RequestBody TransactionDTO transactionDTO) {
		accountService.transferMoney(transactionDTO.getSourceAccount(), transactionDTO.getDestinationAccount(), transactionDTO.getAmount(), transactionDTO.getBankId());
		
		Map<String, Object> resp = new HashMap<>();
		resp.put("date", LocalDate.now().toString());
		resp.put("status", HttpStatus.OK);
		resp.put("message", "successful bank transfer!");
		resp.put("transactionDTO", transactionDTO);
		
		return ResponseEntity.ok(resp);
	}
}