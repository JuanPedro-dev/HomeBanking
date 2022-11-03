package com.santander.hombanking;

import com.santander.hombanking.models.*;
import com.santander.hombanking.repositories.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
// SpringBootTest
public class IntegracionJpaTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clienLoanRepository;

    @Autowired
    CardRepository cardRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    void testFindClientById() {
        Optional<Client> client = clientRepository.findById(1L);
        assertTrue(client.isPresent());
        assertEquals("Melba", client.orElseThrow().getFirstName());
    }

    @Test
    void testFindAccountAll(){
        List<Account> accounts = accountRepository.findAll();
        assertFalse(accounts.isEmpty());

    }
    @Test
    void testFindAccountById(){
        Optional<Account> account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals("VIN001", account.orElseThrow().getNumber());
    }
    @Test
    void testFindAllClients(){
        List<Client> clients = clientRepository.findAll();
        assertFalse(clients.isEmpty());
    }

    @Test
    void testFindALLTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertFalse(transactions.isEmpty());
    }

    @Test
    void testFindTransactionByID(){
        Optional<Transaction> transaction = transactionRepository.findById(1L);
        assertTrue(transaction.isPresent());
        assertEquals(TransactionType.DEBIT, transaction.orElseThrow().getType());
    }

    @Test
    void testFindALLLoand(){
        List<Loan> loans = loanRepository.findAll();
        assertFalse(loans.isEmpty());
    }

    @Test
    void testFindLoanById(){
        Optional<Loan> loan = loanRepository.findById(1L);
        assertTrue(loan.isPresent());
        assertEquals("Hipotecario", loan.orElseThrow().getName());
    }

    @Test
    void testFindAllClientLoan(){
        List<ClientLoan> clientLoans = clienLoanRepository.findAll();
        assertFalse(clientLoans.isEmpty());
    }

    @Test
    void testFindClientLoanById(){
        Optional<ClientLoan> clientLoan = clienLoanRepository.findById(1L);
        assertTrue(clientLoan.isPresent());
        assertEquals(400000, clientLoan.orElseThrow().getAmount());
        assertEquals(60, clientLoan.orElseThrow().getPayments());
    }

    @Test
    void testFindAllCard(){
        List<Card> cards = cardRepository.findAll();
        assertFalse(cards.isEmpty());
    }

    @Test
    void testFindCardById(){
        Optional<Card> cards = cardRepository.findById(1L);
        assertTrue(cards.isPresent());
        assertEquals("Melba Morel", cards.orElseThrow().getCardHolder());
    }

    @Test
    void testClientSave(){
        Client data = new Client("Pepe", "Grillo", "Pepe@gmail.com","123");
        Client client = clientRepository.save(data);

        assertEquals("Pepe", client.getFirstName());
        assertEquals("Grillo", client.getLastName());
        assertEquals("Pepe@gmail.com", client.getEmail());
        assertEquals("123", client.getPassword());
    }

    @Test
    void testAccountSave(){
        Client client = clientRepository.findById(1l).get();
        Account account = new Account("VIN005", LocalDateTime.now(), 5000, client);

        assertEquals("VIN005", account.getNumber());
        assertEquals(5000, account.getBalance());
        assertEquals("Melba", account.getClient().getFirstName());
    }

}