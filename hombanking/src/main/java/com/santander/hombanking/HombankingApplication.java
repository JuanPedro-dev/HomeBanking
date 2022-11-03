package com.santander.hombanking;

import com.santander.hombanking.models.*;
import com.santander.hombanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HombankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HombankingApplication.class, args);
	}
	LocalDateTime localDate = LocalDateTime.now();
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			/** Modelo de relación:
				Cliente 1:
			           cuentas:
			                  cuenta 1
			                         transaccion 1
			                         transaccion 2
			                  cuenta 2
			 			Tarjetas:
			*/

 			// Creo un cliente
			Client client_1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("1234"));

			// Creo una cuenta, y le digo de qué cliente es.
			Account account_1 = new Account("VIN001", localDate,5000.0, client_1);
			Account account_2 = new Account("VIN002_manual",localDate.plusDays(1),7500.0, client_1);

			// Un cliente puede tener muchas cuentas (OneToMany), agrego 2 cuentas de prueba
			client_1.addAccount(account_1);
			client_1.addAccount(account_2);

			// Creo un prestamo
			Loan loan = new Loan("Hipotecario", 400);
			loan.addPayment(30);
			loan.addPayment(50);

			// Creo el enlace entre cliente y loan
			ClientLoan clientLoan = new ClientLoan(400000, 60,client_1, loan);

			// Creo transacción (le menciono por clave foranea a qué cuenta pertenece dicha transacción)
			Transaction transaction1 = new Transaction(TransactionType.DEBIT,"Descripcion_manual 1", localDate,5000, account_1 );
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,"Descripcion_manual 2", localDate.plusDays(1),7500,account_1 );

			// Le agrego a cuenta la transacción
			account_1.addTransaction(transaction1);
			account_1.addTransaction(transaction2);

			// Creo una tarjeta
			LocalDate tiempoSinHs = localDate.toLocalDate();
			Card tarjeta = new Card("Melba Morel", CardColor.GOLD,CardType.DEBIT, "1111-2222-3333-4444", 123, tiempoSinHs, tiempoSinHs.plusYears(5));

			client_1.addCard(tarjeta);

			// Guardo los valores en la BBDD
			clientRepository.save(client_1);
			accountRepository.save(account_1);
			accountRepository.save(account_2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			cardRepository.save(tarjeta);
			loanRepository.save(loan);
			clientLoanRepository.save(clientLoan);

			// Para el admin
			Client admin = new Client("admin", "admin", "admin@admin", passwordEncoder.encode("admin"));
			clientRepository.save(admin);

			//////////////////////////////////////////////////////////////////////////////////////////////////



		};
	}
}

