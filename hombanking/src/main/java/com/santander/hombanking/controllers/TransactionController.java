package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.TransactionDTO;
import com.santander.hombanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping(value = "/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }

    @GetMapping(value = "/transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id){
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }
}
