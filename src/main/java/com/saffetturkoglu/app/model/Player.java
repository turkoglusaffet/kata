package com.saffetturkoglu.app.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Player {
    private String name;
    private int health ;
    private ArrayList<ManaSlot> manaSlots ;
    private int[] deckOfCards;

    public Player(String name) {
        this.health = 30;
        this.manaSlots = new ArrayList<>();
        this.deckOfCards = new int[]{0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8};
        this.name = name;
    }
}
