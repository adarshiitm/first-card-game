package com.adp.cards;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public enum CardNumber {
    NO_VALUE("0"),
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    JOKER_1("JOKER_1"),
    JOKER_2("JOKER_2"),
    FOLDED("FOLDED");

    String displayName;
    static Map<String, CardNumber> displayNameToCardNumberMap;

    static {
        displayNameToCardNumberMap = new HashMap<String, CardNumber>();
        for(CardNumber cardNumber: CardNumber.values()){
            displayNameToCardNumberMap.put(cardNumber.displayName, cardNumber);
        }
    }

    CardNumber(String displayName) {
        this.displayName = displayName;
    }

    public static List<CardNumber> getAllNumbers(){
        List<CardNumber> allNumbers = new ArrayList<CardNumber>(EnumSet.allOf(CardNumber.class));
        allNumbers.remove(NO_VALUE);
        allNumbers.remove(FOLDED);
        allNumbers.remove(JOKER_1);
        allNumbers.remove(JOKER_2);
        return allNumbers;
    }

    public static String[] getAllNumberNames(){
        List<CardNumber> allNumbers = new ArrayList<CardNumber>(EnumSet.allOf(CardNumber.class));
        allNumbers.remove(NO_VALUE);
        String[] allNumberNames = new String[allNumbers.size()];
        for(int i=0;i<allNumbers.size();i++){
            allNumberNames[i] = allNumbers.get(i).displayName;
        }
        return allNumberNames;
    }

    public static String[] getExtraNumberValues() {
        String[] allNumberNames = new String[3];
        for(int i=0;i<3;i++){
            allNumberNames[i] = CardNumber.FOLDED.displayName;
            allNumberNames[i] = CardNumber.JOKER_1.displayName;
            allNumberNames[i] = CardNumber.JOKER_2.displayName;
        }
        return allNumberNames;
    }

    public static CardNumber getCardNumberByDisplayName(String displayName) {
        if(displayNameToCardNumberMap.containsKey(displayName)){
            return displayNameToCardNumberMap.get(displayName);
        }
        throw new RuntimeException("no number found for this diaplay name " + displayName);
    }
}
