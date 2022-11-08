package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.AccountDTO;
import com.santander.hombanking.models.Client;
import com.santander.hombanking.repositories.AccountRepository;
import com.santander.hombanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;

    @GetMapping(value = "/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAllAccounts_JPQL().stream().map(AccountDTO::new).collect(toList());
    }

     @GetMapping(value = "/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        return accountRepository.findById_JPQL(id).map(AccountDTO::new).orElse(null);
    }

    @GetMapping(value = "/clients/current/accounts")
    public Set<AccountDTO> getAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName()).orElse(null);
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    @GetMapping(value = "/clients/current/accounts/{id}")
    public AccountDTO getAccountId(@PathVariable long id, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName()).orElse(null);
        assert client != null;
        Set<AccountDTO> accountDTOSet = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());

        AccountDTO accountDTO = ( (List<AccountDTO>) accountDTOSet.stream()
                .filter(account1 -> (account1.getId() == id)).collect(toList())).get(0);

        return accountDTO;
    }




}
