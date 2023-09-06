package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<TransactionDTO> getTransaction() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(long id) {
        return transactionRepository.findById(id).orElse(null);
    }
}
