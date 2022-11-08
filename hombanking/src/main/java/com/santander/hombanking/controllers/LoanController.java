package com.santander.hombanking.controllers;

import com.santander.hombanking.dtos.LoanApplicationDTO;
import com.santander.hombanking.dtos.LoanDTO;
import com.santander.hombanking.models.Client;
import com.santander.hombanking.models.Loan;
import com.santander.hombanking.repositories.LoanRepository;
import com.santander.hombanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping(value = "/loans")
    public Set<LoanDTO> getAllLoans(){
        return loanService.currentAllLoans();
    }

    @Transactional
    @PostMapping(value = "/loans")
    public ResponseEntity<Object> postTransaction(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        return loanService.addLoan(loanApplicationDTO, authentication);
    }

}
