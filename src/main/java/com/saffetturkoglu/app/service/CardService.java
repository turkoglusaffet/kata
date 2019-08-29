package com.saffetturkoglu.app.service;

import com.saffetturkoglu.app.exception.CardException;
import com.saffetturkoglu.app.model.ManaSlot;
import com.saffetturkoglu.app.model.Player;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Random;

import static com.saffetturkoglu.app.Util.Utils.removeTheElement;

@Log
public class CardService {

    private Random random = new Random();

    // take a card
    public Player takeACard(Player[] players, int playerIndex) {
        Player player = players[playerIndex];
        if(player.getDeckOfCards().length == 0){
            log.info("There is no a card So that");
            log.info(player.getName()+ " won.");
            return null;
        }else {
            int index = getTheRandomIndex(player.getDeckOfCards());
            player.getManaSlots().add(dealRandomACard(player.getDeckOfCards(), index));
            player.setDeckOfCards(removeTheElement(player.getDeckOfCards(), index));
            return player;
        }
    }

    public int getTheTotalCardValue(int totalManaCost, Player player) {
        int lastIndex =
                (totalManaCost < player.getManaSlots().size())
                        ? totalManaCost
                        : player.getManaSlots().size();
        return player
                .getManaSlots()
                .subList(0, lastIndex)
                .stream()
                .mapToInt(ManaSlot::getCardValue)
                .sum();
    }

    public int getTotalManaCost(ArrayList<ManaSlot> manaSlots) {
        return manaSlots.stream().mapToInt(ManaSlot::getManaCost).sum();
    }

    public ManaSlot dealRandomACard(int[] cardDeck, int index) {
        ManaSlot manaSlot = new ManaSlot();
        try {
            manaSlot.setCardValue(cardDeck[index]);
        }catch (Exception e){
            throw new CardException("There is no card in deck");
        }
        // I am setting the random value in the variable. Actually,
        // I watched in youtube and I saw the game playing with a mana cost.
        // ManaCost is a parameter that specifies how many cards to attack.
        manaSlot.setManaCost(random.nextInt(3));
        return manaSlot;
    }

    private int getTheRandomIndex(int[] cardDeck) {
        return random.nextInt(cardDeck.length);
    }
}
