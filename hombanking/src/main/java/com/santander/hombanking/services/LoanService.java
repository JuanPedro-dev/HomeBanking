package com.santander.hombanking.services;

import com.santander.hombanking.dtos.LoanApplicationDTO;
import com.santander.hombanking.dtos.LoanDTO;
import com.santander.hombanking.models.*;
import com.santander.hombanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;
    

    public Set<LoanDTO> currentAllLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toSet());
    }

    @Transactional
//    Debe recibir un objeto de solicitud de crédito con los datos del préstamo
    public ResponseEntity<Object> addLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Loan loanToAdd = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        Client client = clientRepository.findByEmail(authentication.getName()).orElse(null);


//    Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if(loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0){
            return new ResponseEntity<>("El ID y las Coutas, no puede ser negativo ni cero ", HttpStatus.FORBIDDEN);
        }
//    Verificar que el préstamo exista
        if(loanToAdd == null){
            return  new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        }

//    Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if(loanToAdd.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("El prestamo excede el maximo. maximo = " +loanToAdd.getMaxAmount() , HttpStatus.FORBIDDEN);
        }

//    Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        Set<Integer> paymentToAdd = loanToAdd.getPayments().stream().filter(payment -> payment == loanApplicationDTO.getPayments()).collect(Collectors.toSet());

       // if(loanToAdd.getPayments().contains(loanApplicationDTO.getPayments()))
        if(paymentToAdd.size() == 0){
            return new ResponseEntity<>("Las cantidad de coutas no esta disponible", HttpStatus.FORBIDDEN);
        }

//    Verificar que la cuenta de destino exista
        Account toAccount = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber()).orElse(null);

        if(toAccount == null){
            return new ResponseEntity<>("No se encuentra la cuenta destino", HttpStatus.FORBIDDEN);
        }

//    Verificar que la cuenta de destino pertenezca al cliente autenticado
        assert toAccount != null;
        if(!(toAccount.getClient().equals(client))){
            return new ResponseEntity<>("La cuenta no pertenece al usuario logeado", HttpStatus.FORBIDDEN);
        }

//    Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
        double amountToADD = loanApplicationDTO.getAmount() * 1.2;


//    Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo)
//    con la descripción concatenando el nombre del préstamo y la frase “loan approved”
        Transaction transactionLoan = new Transaction(TransactionType.CREDIT, loanToAdd.getName(), LocalDateTime.now(), loanApplicationDTO.getAmount(), toAccount);

//    Se debe actualizar la cuenta de destino sumando el monto solicitado.
        toAccount.setBalance(toAccount.getBalance() + loanApplicationDTO.getAmount());

        // Finalizo guardando y creando el
        ClientLoan clientLoan = new ClientLoan(amountToADD, loanApplicationDTO.getPayments(), client, loanToAdd);
        client.addClientLoan(clientLoan);

        clientRepository.save(client);
        loanRepository.save(loanToAdd);
        accountRepository.save(toAccount);
        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transactionLoan); 

        return new ResponseEntity<>("Delivered Loan", HttpStatus.ACCEPTED);
    }



}
