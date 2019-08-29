package com.saffetturkoglu.app.service;

import com.saffetturkoglu.app.exception.InitialException;
import com.saffetturkoglu.app.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


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

public class GameServiceTest {

    private GameService gameService;
    @Mock private CardService cardServiceMock;
    @Mock private Player figtherPlayer;
    @Mock private Player opponentPlayer;
    @Before
    public void init(){
        initMocks(this);
        this.figtherPlayer = new Player(PLAYER_ONE_NAME);
        this.opponentPlayer = new Player(PLAYER_TWO_NAME);
        gameService = new GameService(figtherPlayer,opponentPlayer,cardServiceMock);
    }
    @Test
    public void shouldCheckTheInitialValue(){
        //WHEN
        gameService.checkThePlayersForInitial();
        //THEN
    }

    @Test(expected = InitialException.class)
    public void shouldThrowInitialExceptionWhenCheckTheInitialValue(){
        //GIVEN
        figtherPlayer.setHealth(0);
        //WHEN
        gameService.checkThePlayersForInitial();
        //THEN
    }

    @Test
    public void shouldDealInitialManaCost(){
        //GIVEN
        when(cardServiceMock.takeACard(any(),anyInt())).thenReturn(createSamplePlayer());
        //WHEN
        gameService.dealInitialManaCost();
        //THEN
        verify(cardServiceMock, times(6)).takeACard(any(),anyInt());

    }

    @Test
    public void shouldReturnTrueWhenCheckingTheHealthHigherThan0(){
        // GIVEN
        figtherPlayer.setHealth(20);
        //WHEN
        boolean isHealth = gameService.checkTheHealth(figtherPlayer);
        //THEN
        Assert.assertTrue(isHealth);
    }
    @Test
    public void shouldReturnFalseWhenCheckingTheHealthLowerThan0(){
        // GIVEN
        figtherPlayer.setHealth(-10);
        //WHEN
        boolean isHealth = gameService.checkTheHealth(figtherPlayer);
        //THEN
        Assert.assertFalse(isHealth);
    }

    @Test
    public void shouldDamageAnOppenent(){

        // GIVEN
        opponentPlayer.setManaSlots(createSampleManaSlot());
        //WHEN
        Player opponentPlayer = gameService.damageAnOpponent(this.opponentPlayer, 20);
        //THEN
        Assert.assertEquals(13,opponentPlayer.getHealth());
    }

    @Test
    public void shouldOppenentPlayersHealthBiggerThan0AfterFightAndReturnTrue(){
        //GIVEN
        when(cardServiceMock.getTotalManaCost(any())).thenReturn(5);
        when(cardServiceMock.getTheTotalCardValue(anyInt(),any())).thenReturn(5);
        //WHEN
        boolean fightResult = gameService.fight(PLAYER_ONE, PLAYER_TWO);
        //THEN
        Assert.assertTrue(fightResult);

    }
    @Test
    public void shouldOppenentPlayersHealthLowerThan0AfterFightAndReturnTrue(){
        //GIVEN
        when(cardServiceMock.getTotalManaCost(any())).thenReturn(15);
        when(cardServiceMock.getTheTotalCardValue(anyInt(),any())).thenReturn(35);
        //WHEN
        boolean fightResult = gameService.fight(PLAYER_ONE, PLAYER_TWO);
        //THEN
        Assert.assertFalse(fightResult);

    }
}
