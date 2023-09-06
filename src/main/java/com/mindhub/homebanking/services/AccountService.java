package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
     List<AccountDTO> getAll();

     void saveAccount(Account account);

     Account findById(long id);

     AccountDTO getAccountDTO(long id);

     Account findByNumber(String number);
}
