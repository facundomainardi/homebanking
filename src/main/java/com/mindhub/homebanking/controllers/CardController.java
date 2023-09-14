package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RestController
    @RequestMapping("/api")
    public class CardController {

        @Autowired
        public ClientService clientService;
        @Autowired
        public CardService cardService;

        @PostMapping("/clients/current/cards")
        public ResponseEntity<Object> registerCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

            Client client = clientService.findClientByEmail(authentication.getName());

            AtomicInteger count= new AtomicInteger();

            if (client != null) {

                Stream<Card> stream = client.getCards().stream();
                stream.forEach((card) -> {if (card.getType() == cardType && card.isCurrent()) count.getAndIncrement();});
                String numberCard = CardUtils.getRandomNumber(1001,10000);
                if (count.get() > 2 && cardService.findCardByNumber(numberCard) == null) {
                    return new ResponseEntity<>("You have already 3 cards of that type", HttpStatus.FORBIDDEN);
                } else {

                    Card card = new Card( client.getFirstName()+" "+client.getLastName(),cardType,
                            cardColor, numberCard,CardUtils.getRandomNumberCvv(101,1000),
                            LocalDate.now(), LocalDate.now().plusYears(5), true);

                    client.addCard(card);

                    cardService.saveCard(card);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
            } else {
                return new ResponseEntity<>("NOT", HttpStatus.FORBIDDEN);
            }
        }


    @PatchMapping("/clients/current/cards/modify/{id}")
    public ResponseEntity<Object> disable(@PathVariable Long id){
        Card card = cardService.findById(id);
        card.setCurrent(false);
        cardService.saveCard(card);
        return new ResponseEntity<>("Set false", HttpStatus.CREATED);

    }




}
