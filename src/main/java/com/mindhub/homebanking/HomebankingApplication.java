package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository repository2, TransactionRepository transactionRepository) {
		return (args) -> {
			// save a couple of client
			Client client1  = new Client("Melba", "Morel",
					"melba@mindhub.com");
			Client client2 = new Client("Juan", "Mainardi",
					"mainardi@mindhub.com");
			Account account1 = new Account( "VIN001", LocalDate.now(), 5000);
			Account account2 = new Account( "VIN002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account( "VIN003", LocalDate.now(), 15000);
			Account account4 = new Account( "VIN004", LocalDate.now(), 72500);
			Account account5 = new Account( "VIN005", LocalDate.now(), 5900);
			Account account6 = new Account( "VIN006", LocalDate.now(), 3500);
			Account account7 = new Account( "VIN007", LocalDate.now(), 9000);
			Account account8 = new Account( "VIN008", LocalDate.now(), 2500);
			Transaction transaction1 = new Transaction(TransactionType.DEBIT,-1000.00,"Impuestos varios", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,10000.00,"Acreditacion de haberes",LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.DEBIT,-3000.00,"CASA", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,5000.00,"BONIF",LocalDateTime.now());
			Transaction transaction5 = new Transaction(TransactionType.DEBIT,-1500.00,"Impuestos varios", LocalDateTime.now());
			Transaction transaction6 = new Transaction(TransactionType.CREDIT,6000.00,"Acreditacion de haberes",LocalDateTime.now());
			Transaction transaction7 = new Transaction(TransactionType.DEBIT,-4000.00,"COMPRA", LocalDateTime.now());
			Transaction transaction8 = new Transaction(TransactionType.CREDIT,3000.00,"BONIF",LocalDateTime.now());

			repository.save(client1);
			repository.save(client2);
			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);
			client2.addAccount(account5);
			client2.addAccount(account6);
			client2.addAccount(account7);
			client2.addAccount(account8);
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account1.addTransaction(transaction4);
			account2.addTransaction(transaction5);
			account2.addTransaction(transaction6);
			account2.addTransaction(transaction7);
			account2.addTransaction(transaction8);
			repository2.save(account1);
			repository2.save(account2);
			repository2.save(account3);
			repository2.save(account4);
			repository2.save(account5);
			repository2.save(account6);
			repository2.save(account7);
			repository2.save(account8);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);
			repository.save(client1);
			repository.save(client2);













		};
	}


	}



