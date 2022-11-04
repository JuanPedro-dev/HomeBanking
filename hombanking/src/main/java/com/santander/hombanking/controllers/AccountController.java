package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.AccountDTO;
import com.santander.hombanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping(value = "/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAllAccounts_JPQL().stream().map(AccountDTO::new).collect(toList());
    }

     @GetMapping(value = "/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        return accountRepository.findById_JPQL(id).map(AccountDTO::new).orElse(null);
    }




}
