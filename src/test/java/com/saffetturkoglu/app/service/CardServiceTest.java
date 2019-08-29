package com.saffetturkoglu.app.service;

import com.saffetturkoglu.app.exception.CardException;
import com.saffetturkoglu.app.exception.InitialException;
import com.saffetturkoglu.app.model.ManaSlot;
import com.saffetturkoglu.app.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static com.saffetturkoglu.app.helpers.Constants.CARD_OF_DECK;
import static com.saffetturkoglu.app.helpers.Constants.PLAYER_ONE;
import static com.saffetturkoglu.app.helpers.Constants.PLAYER_ONE_NAME;
import static com.saffetturkoglu.app.helpers.Constants.PLAYER_TWO;
import static com.saffetturkoglu.app.helpers.Constants.PLAYER_TWO_NAME;
import static com.saffetturkoglu.app.helpers.Util.createSampleManaSlot;
import static com.saffetturkoglu.app.helpers.Util.createSamplePlayer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CardServiceTest {

    private CardService cardService;
    private Player[] players ;
    @Before
    public void init(){
        initMocks(this);
        players = new Player[2];
        this.players[0] = new Player("PLAYER ONE");
        this.players[1] = new Player("PLAYER ONE");
        cardService = new CardService();
    }
    @Test
    public void shouldTakeACardInDeck(){
        //GIVEN
        //WHEN
        Player player = cardService.takeACard(players, PLAYER_ONE);
        //THEN
        Assert.assertFalse(player.getManaSlots().isEmpty());
    }
    @Test
    public void shouldReturnNullWhenThereIsNoCardInDeck(){
        //GIVEN
         players[0].setDeckOfCards(new int[]{});
        //WHEN
        Player player = cardService.takeACard(players, PLAYER_ONE);
        //THEN
        Assert.assertNull(player);
    }

    @Test
    public void shouldGetTheTotalCardValue(){
        //GIVEN
        Player player = cardService.takeACard(players, PLAYER_ONE);
        int expectedSum = player
                .getManaSlots().get(0).getCardValue();
        //WHEN
        int totalCardValue = cardService.getTheTotalCardValue(1, player);
        //THEN
        Assert.assertEquals(expectedSum,totalCardValue);
    }
    @Test
    public void shouldGetTotalManaCost(){
        //GIVEN
        ArrayList<ManaSlot> sampleManaSlot = createSampleManaSlot();
        int expectedTotalManaCost = 5;
        //WHEN
        int totalManaCost = cardService.getTotalManaCost(sampleManaSlot);
        //THEN
        Assert.assertEquals(expectedTotalManaCost,totalManaCost);
    }

    @Test
    public void shouldDealRandomACard(){

        //WHEN
        ManaSlot manaSlot = cardService.dealRandomACard(CARD_OF_DECK, PLAYER_ONE);
        //THEN
        Assert.assertNotNull(manaSlot);
        Assert.assertNotNull(manaSlot.getManaCost());
        Assert.assertNotNull(manaSlot.getCardValue());
    }

    @Test(expected = CardException.class)
    public void shouldThrowExceptionWhenDealRandomACard(){
        //WHEN
        cardService.dealRandomACard(new int[]{}, PLAYER_ONE);
    }
}
