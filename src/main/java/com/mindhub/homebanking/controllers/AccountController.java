package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/accounts")
    public List<AccountDTO> getAll(){return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());}
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){return accountRepository.findById(id).map(AccountDTO::new).orElse(null);}
    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getAccountsClients(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccount().stream().map(AccountDTO::new).collect(toList());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if(client != null){
            if(client.getAccount().size() > 2){
                return new ResponseEntity<>("You have already 3 accounts", HttpStatus.FORBIDDEN);
            }else{
                Account account = new Account("VIN-"+getRandomNumber(100001,100000000), LocalDate.now(),0.00);

                client.addAccount(account);

                accountRepository.save(account);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>("INVALID", HttpStatus.FORBIDDEN);
        }
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
