package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.LoanApplicationDTO;
import com.santander.hombanking.models.Loan;
import com.santander.hombanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Transactional
    @PostMapping(value = "/loans")
    public ResponseEntity<Object> postTransaction(@RequestBody LoanApplicationDTO loanApplicationDTO){

        return new ResponseEntity<>("llamar al services", HttpStatus.ACCEPTED);
    }

}
