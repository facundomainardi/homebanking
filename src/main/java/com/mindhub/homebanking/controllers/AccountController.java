package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AcoountController {

        @Autowired
        private AccountRepository repo;

        @RequestMapping("/accounts")
        public List<AccountDTO> getAll(){
            return repo.findAll().stream().map(AccountDTO::new).collect(toList());
        }

        @RequestMapping("/accounts/{id}")
        public AccountDTO getAccout(@PathVariable Long id){
            return repo.findById(id).map(AccountDTO::new).orElse(null);
        }


}
