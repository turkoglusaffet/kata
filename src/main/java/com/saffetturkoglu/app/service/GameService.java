package com.saffetturkoglu.app.service;

import com.saffetturkoglu.app.exception.InitialException;
import com.saffetturkoglu.app.model.ManaSlot;
import com.saffetturkoglu.app.model.Player;
import lombok.extern.java.Log;

import static com.saffetturkoglu.app.Util.Constants.INITIAL_HEALTH;
import static com.saffetturkoglu.app.Util.Constants.PLAYER_ONE;
import static com.saffetturkoglu.app.Util.Constants.PLAYER_TWO;

@Log
public class GameService {
    private CardService cardService;
    private Player[] players = new Player[2];

    public GameService(Player fighterPlayer,Player opponentPlayer,CardService cardService) {
        this.players[0] = fighterPlayer;
        this.players[1] = opponentPlayer;
        this.cardService = cardService;
    }

    public boolean run() {
        checkThePlayersForInitial();
        dealInitialManaCost();
        startGame(PLAYER_ONE, PLAYER_TWO);
        return true;
    }

    // game starting ...
    private void startGame(int figter, int oppenent) {
        log.info("Game is starting");
        players[figter] = cardService.takeACard(players, figter); // playerOne take a card
        if (players[figter] != null && fight(figter, oppenent)) {
            startGame(oppenent, figter);
        }
    }

    public boolean fight(int fighterIndex, int opponentIndex) {
        Player fighterPlayer = players[fighterIndex];
        Player opponentPlayer = players[opponentIndex];
        log.info("Fighter Player is "+ fighterPlayer.getName());
        log.info("Opponent Player is "+ opponentPlayer.getName());
        int totalManaCost = cardService.getTotalManaCost(fighterPlayer.getManaSlots());
        log.info("Total ManaCost is "+ totalManaCost);
        int damageValue = cardService.getTheTotalCardValue(totalManaCost, fighterPlayer);
        log.info("Damage Value is "+ damageValue);
        opponentPlayer = damageAnOpponent(opponentPlayer, damageValue);
        return checkTheHealth(opponentPlayer);
    }

    public boolean checkTheHealth(Player player) {

        log.info("Checking the Health Of Opponent Player");
        if (player.getHealth() <= 0) {
            log.info(player.getName() + " won ");
            return false;
        } else return true;
    }

    public Player damageAnOpponent(Player opponentPlayer, int damageValue) {
        while(damageValue != 0 && !opponentPlayer.getManaSlots().isEmpty()){
            ManaSlot manaSlot = opponentPlayer.getManaSlots().get(0);
            int cardValue = manaSlot.getCardValue();
            opponentPlayer.getManaSlots().remove(manaSlot);
            if (damageValue > cardValue) {
                damageValue = damageValue - cardValue;
            } else {
                manaSlot.setCardValue(cardValue - damageValue);
                opponentPlayer.getManaSlots().add(manaSlot);
            }
        }
        if (damageValue > 0) {
            opponentPlayer.setHealth(opponentPlayer.getHealth() - damageValue);
        }
        return opponentPlayer;
    }

    public void checkThePlayersForInitial() {
        // Control the variables for setting initial variables
        log.info("Checking players values");
        for (Player player : players) {
            if (player.getHealth() != INITIAL_HEALTH || !player.getManaSlots().isEmpty()) {
                throw new InitialException("Players haven't initial values");
            }
        }
    }

    // each player take the 3 cards
    public void dealInitialManaCost() {
        log.info("Each player take the 3 cards from own deck");
        for (int i = 0; i < 3; i++) {
            players[PLAYER_ONE] = cardService.takeACard(players, PLAYER_ONE);
            players[PLAYER_TWO] = cardService.takeACard(players, PLAYER_TWO);
        }
    }
}
