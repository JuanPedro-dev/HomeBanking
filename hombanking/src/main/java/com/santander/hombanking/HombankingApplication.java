package com.santander.hombanking;

import com.santander.hombanking.models.Account;
import com.santander.hombanking.models.Client;
import com.santander.hombanking.models.Transaction;
import com.santander.hombanking.models.TransactionType;
import com.santander.hombanking.repositories.AccountRepository;
import com.santander.hombanking.repositories.ClientRepository;
import com.santander.hombanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HombankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HombankingApplication.class, args);
	}
	LocalDateTime localDate = LocalDateTime.now();
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args) -> {
			/** Modelo de relación:
				Cliente 1:
			           cuentas:
			                  cuenta 1
			                         transaccion 1
			                         transaccion 2
			                  cuenta 2


			*/
			// Creo un cliente
			Client client_1 = new Client("Melba_manual", "Morel", "melba@mindhub.com");

			// Creo una cuenta, y le digo de qué cliente es.
			Account account_1 = new Account("VIN001_manual", localDate,5000.0, client_1);
			Account account_2 = new Account("VIN002_manual",localDate.plusDays(1),7500.0, client_1);

			// Un cliente puede tener muchas cuentas (OneToMany), agrego 2 cuentas de prueba
			client_1.addAccount(account_1);
			client_1.addAccount(account_2);


			// Creo transacción (le menciono por clave foranea a qué cuenta pertenece dicha transacción)
			Transaction transaction1 = new Transaction(TransactionType.DEBIT,"Descripcion_manual 1", localDate,5000, account_1 );
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,"Descripcion_manual 2", localDate.plusDays(1),7500,account_1 );

			// Le agrego a cuenta la transacción
			account_1.addTransaction(transaction1);
			account_1.addTransaction(transaction2);

			// Creo una instancia de la tabla con cliente, pretamos con cantidad y coutas...


			// Guardo los valores en la BBDD
			clientRepository.save(client_1);
			accountRepository.save(account_1);
			accountRepository.save(account_2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
		};
	}
}

