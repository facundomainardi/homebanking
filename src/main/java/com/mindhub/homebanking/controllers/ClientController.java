package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AccountRepository accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());}
    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {return clientRepository.findById(id).map(ClientDTO::new).orElse(null);}
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

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        Account account = new Account("VIN-"+getRandomNumber(10000001,100000000), LocalDate.now(),0.00);

        client.addAccount(account);

        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
