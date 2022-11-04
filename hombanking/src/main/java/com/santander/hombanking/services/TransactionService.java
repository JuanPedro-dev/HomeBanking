package com.santander.hombanking.services;

import com.santander.hombanking.dtos.TransactionDTO;
import com.santander.hombanking.models.Account;
import com.santander.hombanking.models.Client;
import com.santander.hombanking.models.Transaction;
import com.santander.hombanking.models.TransactionType;
import com.santander.hombanking.repositories.AccountRepository;
import com.santander.hombanking.repositories.ClientRepository;
import com.santander.hombanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

//        Debe recibir el monto, la descripción, número de cuenta de origen y número de cuenta de destino como parámetros de solicitud
    public ResponseEntity<Object> verifyTransactions(@RequestParam String fromAccountNumber,
                                                     @RequestParam String toAccountNumber,
                                                     @RequestParam double amount,
                                                     @RequestParam String description,
                                                     Authentication authentication
    ){

//        Verificar que los parámetros no estén vacíos
        if(fromAccountNumber.isEmpty()){
            return new ResponseEntity<>("Account origin is empty", HttpStatus.FORBIDDEN);
        }
        if(toAccountNumber.isEmpty()){
            return new ResponseEntity<>("Account destination is empty", HttpStatus.FORBIDDEN);
        }
        if(amount < 0){
            return new ResponseEntity<>("Amount can´t be negative", HttpStatus.FORBIDDEN);
        }
        if(description.isEmpty()){
            return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
        }

//        Verificar que los números de cuenta no sean iguales
        if(fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("You can't transfer yourself", HttpStatus.FORBIDDEN);
        }

//        Verificar que exista la cuenta de origen
        Client currentClient = clientRepository.findByEmail(authentication.getName()).orElse(null);
        Account fromAccount = accountRepository.findByNumber(fromAccountNumber).orElse(null);

        if(fromAccount == null){
            return new ResponseEntity<>("Account origin doesn`t existe", HttpStatus.FORBIDDEN);
        }

//        Verificar que la cuenta de origen pertenezca al cliente autenticado
        List<Account> accountsCurrent = accountRepository.findByClient(currentClient);

        if(accountsCurrent == null){
            return new ResponseEntity<>("The current client does´t have an account", HttpStatus.FORBIDDEN);
        }

        List<Account> accountCurrent =  accountsCurrent.stream().filter(account -> !(account.getNumber().equals(fromAccountNumber))).collect(toList());

        if(accountCurrent.size() == 0){
            return new ResponseEntity<>("This account dont belong to the current client", HttpStatus.FORBIDDEN);
        }

//        Verificar que exista la cuenta de destino
        Account toAccount = accountRepository.findByNumber(toAccountNumber).orElse(null);
        if( toAccount == null){
            return new ResponseEntity<>("Account destiny does´t exist", HttpStatus.FORBIDDEN);
        }

//        Verificar que la cuenta de origen tenga el monto disponible.
        if(fromAccount.getBalance() < amount){
            return new ResponseEntity<>("Account origin don`t have enough money", HttpStatus.FORBIDDEN);
        }



//        Se deben crear dos transacciones, una con el tipo de transacción “DEBIT” asociada a la cuenta
//        de origen y la otra con el tipo de transacción “CREDIT” asociada a la cuenta de destino.
        Transaction transactionOrigin = new Transaction(TransactionType.DEBIT, description + " => To : " + toAccountNumber, LocalDateTime.now(), -amount, fromAccount);
        Transaction transactionDestiny = new Transaction(TransactionType.CREDIT, description + " => From : " + fromAccountNumber, LocalDateTime.now(), amount, toAccount);

//        A la cuenta de origen se le restará el monto indicado en la petición y a la cuenta de destino se le sumará el mismo monto.
        toAccount.setBalance(toAccount.getBalance() + amount);
        fromAccount.setBalance(fromAccount.getBalance() - amount);

//        fromAccount.addTransaction(transactionOrigin);
//        toAccount.addTransaction(transactionDestiny);

        transactionOrigin.setAccount(fromAccount);
        transactionDestiny.setAccount(toAccount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestiny);

        return new ResponseEntity<>("Éxito", HttpStatus.CREATED);
    }


}
