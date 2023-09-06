package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CardService {
    List<CardDTO> getCards();

    void saveCard(Card card);

    Card findById(long id);

    Card findCardByNumber(String number);

}
