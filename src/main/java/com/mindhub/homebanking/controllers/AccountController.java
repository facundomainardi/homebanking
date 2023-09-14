package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @RequestMapping("/accounts")
    public List<AccountDTO> getAll(){
        return accountService.getAll();
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccountDTO(id);}
    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getAccountsClients(Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        return client.getAccount().stream().map(AccountDTO::new).collect(toList());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication,  @RequestParam TypeAccounts typeAccounts) {

        Client client = clientService.findClientByEmail(authentication.getName());
        AtomicInteger count= new AtomicInteger();
        if(client != null){
            Stream<Account> stream = client.getAccount().stream();
            stream.forEach((account) -> {if (account.isActive()) count.getAndIncrement();});


            if(count.get() > 2){
                return new ResponseEntity<>("You have already 3 accounts", HttpStatus.FORBIDDEN);
            }else{
                int numberAccount = getRandomNumber(100001,100000000);
                if (accountService.findByNumber("VIN-"+numberAccount) ==null){
                    Account account = new Account("VIN-"+numberAccount, LocalDate.now(),0.00,typeAccounts);
                    client.addAccount(account);
                    accountService.saveAccount(account);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>("Account already exist", HttpStatus.FORBIDDEN);                }
            }
        }else{
            return new ResponseEntity<>("INVALID", HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/clients/current/accounts/modify/{id}")
    public ResponseEntity<Object> smartDelete(@PathVariable Long id) {
        Account account = accountService.findById(id);


        if (account.getBalance() > 0) {
            return new ResponseEntity<>("You cannot delete the account with balance", HttpStatus.FORBIDDEN);
        }

        account.setActive(false);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Deleted account", HttpStatus.CREATED);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
