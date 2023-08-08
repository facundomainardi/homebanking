package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    Set<AccountDTO> accounts ;
    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

        this.accounts = client.getAccount().stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toSet());

    }


    public String getFirstName() {
        return firstName;
    }

    public Set<AccountDTO> getAccount() {
        return accounts;
    }

    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }



}
