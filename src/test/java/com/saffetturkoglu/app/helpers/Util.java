package com.saffetturkoglu.app.helpers;

import com.saffetturkoglu.app.model.ManaSlot;
import com.saffetturkoglu.app.model.Player;

import java.util.ArrayList;

public class Util {

    public static Player createSamplePlayer(){
        return new Player("Player Sample");
    }

    public static ArrayList<ManaSlot> createSampleManaSlot(){
        ArrayList<ManaSlot> arrayList = new ArrayList();
        arrayList.add(new ManaSlot(1,2));
        arrayList.add(new ManaSlot(2,1));
        arrayList.add(new ManaSlot(0,2));
        return arrayList;
    }
}
