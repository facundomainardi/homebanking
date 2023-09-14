package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardUtilsTests {
    @Test

    public void cardNumberIsCreated(){

        String cardNumber = CardUtils.getRandomNumber(1001,10000);

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
}
