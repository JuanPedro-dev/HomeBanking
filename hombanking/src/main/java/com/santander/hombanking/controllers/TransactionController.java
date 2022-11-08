package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.TransactionDTO;
import com.santander.hombanking.repositories.TransactionRepository;
import com.santander.hombanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }

    @GetMapping(value = "/transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id){
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    @Transactional
    @PostMapping(value = "/transactions")
    public ResponseEntity<Object> postTransaction(@RequestParam String fromAccountNumber,
                                                  @RequestParam String toAccountNumber,
                                                  @RequestParam double amount,
                                                  @RequestParam String description,
                                                  Authentication authentication){

        String message = transactionService.verifyTransactions(fromAccountNumber, toAccountNumber, amount, description, authentication);

        if(message.equals("success")){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }


}
