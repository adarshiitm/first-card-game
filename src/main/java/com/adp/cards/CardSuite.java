package com.adp.cards;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public enum CardSuite {
    CLUBS(CardColor.BLACK),
    DIAMOND(CardColor.RED),
    HEART(CardColor.RED),
    SPADE(CardColor.BLACK),
    NO_SUITE(CardColor.NO_COLOR);

    private CardColor cardColor;

    CardSuite(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public static List<CardSuite> getAllValidSuites(){
        List<CardSuite> allSuites = new ArrayList<>(EnumSet.allOf(CardSuite.class));
        allSuites.remove(NO_SUITE);
        return allSuites;
    }

    public static List<CardSuite> getAllSuites(){
        return new ArrayList<>(EnumSet.allOf(CardSuite.class));
    }

    public static String[] getAllSuiteNames(){
        List<CardSuite> allSuites = getAllSuites();
        String[] allSuiteNames = new String[allSuites.size()];
        for(int i=0;i<allSuites.size();i++){
            allSuiteNames[i] = allSuites.get(i).name();
        }
        return allSuiteNames;
    }
}
