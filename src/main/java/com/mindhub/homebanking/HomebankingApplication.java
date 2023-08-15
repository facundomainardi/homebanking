package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
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

			List<Integer> payments1 = new ArrayList<>(Arrays.asList(12,24,36,48,60));
			Loan loan1 = new Loan("Hipotecario",500.000,payments1);
			List<Integer> payments2 = new ArrayList<>(Arrays.asList(6,12,24));
			Loan loan2 = new Loan("Personal",100.000,payments2);
			List<Integer> payments3 = new ArrayList<>(Arrays.asList(6,12,24,36));
			Loan loan3 = new Loan("Automotriz",300.000,payments3);

			ClientLoan clientLoan1 = new ClientLoan(400000.00,60);
			ClientLoan clientLoan2 = new ClientLoan(50000.00,12);
			ClientLoan clientLoan3 = new ClientLoan(100000.00,24);
			ClientLoan clientLoan4 = new ClientLoan(200000.00,36);

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.DEBIT ,CardColor.GOLD,
					"5555-9859-3258-0022",613, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT ,CardColor.TITANIUM,
					"6666-5555-4444-3333",951, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(),CardType.CREDIT ,CardColor.SILVER,
					"3215-1478-2564-6582",203, LocalDate.now(), LocalDate.now().plusYears(5));

			clientRepository.save(client1);
			clientRepository.save(client2);
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

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);

			client2.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);
			accountRepository.save(account5);
			accountRepository.save(account6);
			accountRepository.save(account7);
			accountRepository.save(account8);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			clientRepository.save(client1);
			clientRepository.save(client2);
		};
	}


	}



