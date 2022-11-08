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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
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
    @Autowired
    MessageSource messageSource;

//        Debe recibir el monto, la descripción, número de cuenta de origen y número de cuenta de destino como parámetros de solicitud
    public String verifyTransactions(@RequestParam String fromAccountNumber,
                                                     @RequestParam String toAccountNumber,
                                                     @RequestParam double amount,
                                                     @RequestParam String description,
                                                     Authentication authentication
    ){

//        Verificar que los parámetros no estén vacíos
        String message = messageSource.getMessage( "fromAccount.validation.isempty", null, LocaleContextHolder.getLocale());

        if(fromAccountNumber.isEmpty()){
//            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            return message;
        }
        if(toAccountNumber.isEmpty()){
            message = messageSource.getMessage( "toAccount.validation.isempty", null, LocaleContextHolder.getLocale());
            return message;
        }
        if(amount < 0){
            message = messageSource.getMessage( "amount.validation.isPositive", null, LocaleContextHolder.getLocale());
            return message;
        }
        if(description.isEmpty()){
            message = messageSource.getMessage( "description.validation.isEmpty", null, LocaleContextHolder.getLocale());
            return message;
        }

//        Verificar que los números de cuenta no sean iguales
        if(fromAccountNumber.equals(toAccountNumber)){
            message = messageSource.getMessage( "fromAccountNumber.equals.toAccountNumber", null, LocaleContextHolder.getLocale());
            return message;
        }

//        Verificar que exista la cuenta de origen
        Client currentClient = clientRepository.findByEmail(authentication.getName()).orElse(null);
        Account fromAccount = accountRepository.findByNumber(fromAccountNumber).orElse(null);

        if(fromAccount == null){
            message = messageSource.getMessage( "fromAccount.valite.exist", null, LocaleContextHolder.getLocale());
            return message;
        }

//        Verificar que la cuenta de origen pertenezca al cliente autenticado
        List<Account> accountsCurrent = accountRepository.findByClient(currentClient);

        if(accountsCurrent == null){
            message = messageSource.getMessage( "client.validate.account.exist", null, LocaleContextHolder.getLocale());
            return message;
        }

        List<Account> accountCurrent =  accountsCurrent.stream().filter(account -> (account.getNumber().equals(fromAccountNumber))).collect(toList());

        if(accountCurrent.size() == 0){
            message = messageSource.getMessage( "client.validate.account.equals.client", null, LocaleContextHolder.getLocale());
            return message;
        }

//        Verificar que exista la cuenta de destino
        Account toAccount = accountRepository.findByNumber(toAccountNumber).orElse(null);
        if( toAccount == null){
            message = messageSource.getMessage( "toAccount.valida.exist", null, LocaleContextHolder.getLocale());
            return message;
        }

//        Verificar que la cuenta de origen tenga el monto disponible.
        if(fromAccount.getBalance() < amount){
            message = messageSource.getMessage( "fromAccount.validate.limit", null, LocaleContextHolder.getLocale());
            return message;
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

        message = messageSource.getMessage( "success", null, LocaleContextHolder.getLocale());
        return message;
    }


}
