package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();

    void saveLoan(Loan loan);

    Loan findLoanById(long id);

    LoanDTO getLoanDTO(long id);
}
