package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client owner;
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {

    }

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

   public Account(Client owner, String number, LocalDate creationDate, double balance) {
        this.owner = owner;
        this.number = number;
         this.creationDate = creationDate;
         this.balance = balance;
    }
    public long getId() {return id;}
    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}
    public LocalDate getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
  //  @JsonIgnore
    public Client getOwner(){return owner;}
    public void setOwner(Client owner){this.owner = owner;}
    public Set<Transaction> getTransactions() {return transactions;}
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

}
