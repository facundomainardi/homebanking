package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getTransaction();

    void saveTransaction(Transaction transaction);

    Transaction findById(long id);
}
