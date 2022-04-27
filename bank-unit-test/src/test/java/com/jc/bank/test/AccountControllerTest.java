package com.jc.bank.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.bank.controller.AccountController;
import com.jc.bank.dto.TransactionDTO;
import com.jc.bank.entity.Account;
import com.jc.bank.service.AccountService;
import com.jc.bank.utils.Data;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Clase de prueba de un Controller
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void configure() {
		objectMapper = new ObjectMapper();
	}
	
	
	@Test
	public void testGetDeatils() throws Exception {
		when(accountService.findAccountById(1L)).thenReturn(Data.createAccount001().orElseThrow());
		
		mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Juan"))
				.andExpect(jsonPath("$.balance").value("1000"));
		
		verify(accountService).findAccountById(1L);
	}
	
	@Test
	public void testTransferMoney() throws Exception {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setSourceAccount(1L);
		transactionDTO.setDestinationAccount(2L);
		transactionDTO.setAmount(new BigDecimal(100));
		transactionDTO.setBankId(1L);
		
		System.out.println(objectMapper.writeValueAsString(transactionDTO));
		
		Map<String, Object> resp = new HashMap<>();
		resp.put("date", LocalDate.now().toString());
		resp.put("status", HttpStatus.OK);
		resp.put("message", "successful bank transfer!");
		resp.put("transactionDTO", transactionDTO);
		
		System.out.println(objectMapper.writeValueAsString(resp));
		
		mockMvc.perform(post("/api/accounts/transference").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transactionDTO)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.message").value("successful bank transfer!"))
				.andExpect(jsonPath("$.transactionDTO.sourceAccount").value(transactionDTO.getSourceAccount()))
				.andExpect(content().json(objectMapper.writeValueAsString(resp)));
	}
	
	@Test
	void testGetAllAccunts() throws JsonProcessingException, Exception {
		List<Account> accountsLst = Arrays.asList(Data.createAccount001().orElseThrow(), Data.createAccount002().orElseThrow());
		
		when(accountService.findAllAccounts()).thenReturn(accountsLst);
		
		mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name").value("Juan"))
				.andExpect(jsonPath("$[1].name").value("Carlos"))
				.andExpect(jsonPath("$[0].balance").value("1000"))
				.andExpect(jsonPath("$[1].balance").value("2000"))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().json(objectMapper.writeValueAsString(accountsLst)));
		
		verify(accountService).findAllAccounts();
	}
	
	@Test
	void testCreateAccount() throws JsonProcessingException, Exception {
		Account account = new Account(null, "JC", new BigDecimal(3000));
		
		when(accountService.createAccount(any())).then(invocation -> {
			Account a = invocation.getArgument(0);
			a.setId(3L);
			return a;
		});
		
		mockMvc.perform(post("/api/accounts").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(account)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(3)))
				.andExpect(jsonPath("$.name", is("JC")))
				.andExpect(jsonPath("$.balance", is(3000)));
				
		
		verify(accountService).createAccount(any());
	}
}