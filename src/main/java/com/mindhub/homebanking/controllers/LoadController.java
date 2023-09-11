package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LoadController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private LoanService loanService;

   @Autowired
   private ClientLoanService clientLoanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client clientConnect = clientService.findClientByEmail(authentication.getName());
        if(clientConnect == null) return new ResponseEntity<>("Client isnt authorization", HttpStatus.FORBIDDEN);
        System.out.println(loanApplicationDTO.toString());
        if(loanApplicationDTO.getLoanId() == 0 ||
                loanApplicationDTO.getPayments() == 0 ||
                loanApplicationDTO.getToAccountNumber().isEmpty() ||
                loanApplicationDTO.getAmount() <= 0)
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        Loan loan = loanService.findLoanById(loanApplicationDTO.getLoanId());
        if(loan == null) return new ResponseEntity<>("There is no such type of loan", HttpStatus.FORBIDDEN);

        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()) return new ResponseEntity<>("Loan exceeded", HttpStatus.FORBIDDEN);

        if(!loan.getPayments().contains(loanApplicationDTO.getPayments())) return new ResponseEntity<>("not allowed", HttpStatus.FORBIDDEN);

        Account accountDestino = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
        if(accountDestino == null) return new ResponseEntity<>("Account dosent exist", HttpStatus.FORBIDDEN);

        if(!clientConnect.getAccount().contains(accountDestino)) return new ResponseEntity<>("Account dosent exist", HttpStatus.FORBIDDEN);;

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()*1.2,loanApplicationDTO.getPayments());

        clientConnect.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        clientLoanService.saveClientLoan(clientLoan);

        Transaction transactionDestino = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.getName()+" - loan approved", LocalDateTime.now());

        accountDestino.addTransaction(transactionDestino);
        transactionService.saveTransaction(transactionDestino);

        accountDestino.setBalance(accountDestino.getBalance()+loanApplicationDTO.getAmount());
        accountService.saveAccount(accountDestino);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }




    @RequestMapping("/api/loans")
    public List<LoanDTO> getAll(){
        return loanService.getLoans();
    }
}
