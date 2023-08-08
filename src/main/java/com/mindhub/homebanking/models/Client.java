package com.mindhub.homebanking.models;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();
    private String firstName;
    private String lastName;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Client() { }

    public Client(String first, String last, String email) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
    }

    public Client(Set<Account> accounts, String firstName, String lastName, String email) {
        this.accounts = accounts;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public Set<Account> getAccount(){
        return accounts;
    }

    public long getId() {
        return id;
    }

    public void addAccount (Account account){
        account.setOwner(this);
        accounts.add(account);
    }
}
