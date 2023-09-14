package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TypeAccounts;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AccountService accountService;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();}
    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTO(id);}
    @PostMapping("/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()){
            return new ResponseEntity<>("FIRST NAME IS EMPTY", HttpStatus.FORBIDDEN);
        }
        else if (lastName.isEmpty()){
            return new ResponseEntity<>("LAST NAME IS EMPTY", HttpStatus.FORBIDDEN);
        }
        else if(email.isEmpty()){
            return new ResponseEntity<>("EMAIL IS EMPTY", HttpStatus.FORBIDDEN);
        }
        else if(password.isEmpty()){
            return new ResponseEntity<>("PASSWORD IS EMPTY", HttpStatus.FORBIDDEN);
        }

        if (clientService.findClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        int numberAccount = getRandomNumber(100001,100000000);
        if (accountService.findByNumber("VIN-"+numberAccount) == null) {
            Account account = new Account("VIN-" + numberAccount, LocalDate.now(), 0.00, TypeAccounts.AHORRO);
            client.addAccount(account);
            accountService.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Account already Exist", HttpStatus.FORBIDDEN);
        }
           // Account account = new Account("VIN-"+getRandomNumber(10000001,100000000), LocalDate.now(),0.00);

    }
    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return clientService.getCurrent(authentication.getName());
    }
    public int getRandomNumber(int min, int max) {

        return (int) ((Math.random() * (max - min)) + min);
    }
}
