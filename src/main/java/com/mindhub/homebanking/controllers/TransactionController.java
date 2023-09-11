package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> createTransactions(Authentication authentication, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam Double amount, @RequestParam String description) {

        Client client = clientService.findClientByEmail(authentication.getName());
        if (client == null) return new ResponseEntity<>("Client not authorization", HttpStatus.FORBIDDEN);

        if (amount == null ||
                description.isEmpty() ||
                fromAccountNumber.isEmpty() ||
                toAccountNumber.isEmpty())
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        if (fromAccountNumber.equals(toAccountNumber))
            return new ResponseEntity<>("Accounts must be not the same", HttpStatus.FORBIDDEN);

        Account accountFrom = accountService.findByNumber(fromAccountNumber);
        Account accountTo = accountService.findByNumber(toAccountNumber);

        if (accountFrom == null) return new ResponseEntity<>("Account not exist", HttpStatus.FORBIDDEN);
        if (accountTo == null) return new ResponseEntity<>("Account not exist", HttpStatus.FORBIDDEN);

        if (accountFrom.getOwner() != client)
            return new ResponseEntity<>("Change the account", HttpStatus.FORBIDDEN);

        if (accountFrom.getBalance() < amount)
            return new ResponseEntity<>("Your Balance  enougth", HttpStatus.FORBIDDEN);

        if (amount<=0)
            return new ResponseEntity<>("Amount  invalid", HttpStatus.FORBIDDEN);

        Transaction transactionFrom = new Transaction(TransactionType.DEBIT, amount * (-1), description + " - Cuenta: " + fromAccountNumber, LocalDateTime.now());
        Transaction transactionTo = new Transaction(TransactionType.CREDIT, amount, description + " - Cuenta: " + toAccountNumber, LocalDateTime.now());

        accountFrom.addTransaction(transactionFrom);
        accountTo.addTransaction(transactionTo);

        transactionService.saveTransaction(transactionFrom);
        transactionService.saveTransaction(transactionTo);

        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountTo.setBalance(accountTo.getBalance() + amount);

        accountService.saveAccount(accountFrom);
        accountService.saveAccount(accountTo);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
