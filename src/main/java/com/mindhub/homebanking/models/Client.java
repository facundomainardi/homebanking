package com.mindhub.homebanking.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    private String password;
    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();
    public Client() { }
    public Client(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
    }
    public Client(Set<Account> accounts, String firstName, String lastName, String email, String password) {
        this.accounts = accounts;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public Set<Account> getAccount(){
        return accounts;
    }

    public void addAccount (Account account){
        account.setOwner(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
    @JsonIgnore
    public Set<Loan> getLoan(){return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toSet());}
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public Set<Card> getCards() {return cards;}
    public void setCards(Set<Card> cards) {this.cards = cards;}
    public void addCard(Card card) {
        card.setClient(this);
        cards.add(card);
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
