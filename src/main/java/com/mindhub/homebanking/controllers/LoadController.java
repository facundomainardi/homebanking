package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanCreateDTO;
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
        if(loanApplicationDTO.getLoanId() == 0)
            return new ResponseEntity<>("ID INVALID", HttpStatus.FORBIDDEN);
         if( loanApplicationDTO.getPayments() == 0)
             return new ResponseEntity<>("Payments Invalid", HttpStatus.FORBIDDEN);
         if(loanApplicationDTO.getToAccountNumber().isEmpty())
             return new ResponseEntity<>("Account Number is empty", HttpStatus.FORBIDDEN);
         if(loanApplicationDTO.getAmount() <= 0)
            return new ResponseEntity<>("Amount empty", HttpStatus.FORBIDDEN);

        Loan loan = loanService.findLoanById(loanApplicationDTO.getLoanId());
        if(loan == null) return new ResponseEntity<>("There is no such type of loan", HttpStatus.FORBIDDEN);

        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()) return new ResponseEntity<>("Loan exceeded", HttpStatus.FORBIDDEN);

        if(!loan.getPayments().contains(loanApplicationDTO.getPayments())) return new ResponseEntity<>("not allowed", HttpStatus.FORBIDDEN);

        Account accountDestino = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
        if(accountDestino == null) return new ResponseEntity<>("Account dosent exist", HttpStatus.FORBIDDEN);

        if(!clientConnect.getAccount().contains(accountDestino)) return new ResponseEntity<>("Does not belong to the authenticated client", HttpStatus.FORBIDDEN);;

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

    @Transactional
    @PostMapping ("/api/loansType")
    public ResponseEntity<Object> createLoan(@RequestBody LoanCreateDTO loanCreateDTO)       {
        if (loanCreateDTO.getName()==null){
            return new ResponseEntity<>("You must select a name for the loan type", HttpStatus.FORBIDDEN);
        }
        if (loanCreateDTO.getMaxAmount()<=0 || loanCreateDTO.getMaxAmount()==null){
            return new ResponseEntity<>("You must select a positive maximum amount", HttpStatus.FORBIDDEN);
        }
        if (loanCreateDTO.getPayments().size()==0){
            return new ResponseEntity<>("You must add at least one available quota", HttpStatus.FORBIDDEN);
        }
        Loan loan= new Loan(loanCreateDTO.getName(), loanCreateDTO.getMaxAmount(), loanCreateDTO.getPayments());
        loanService.saveLoan(loan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/api/loans")
    public List<LoanDTO> getAll(){
        return loanService.getLoans();
    }
}
