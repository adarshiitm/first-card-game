package com.adp.cards;

import java.util.*;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public class CardHand {
    Set<Card> deck;

    public CardHand(Set<Card> deck) {
        this.deck = deck;
    }

    public Set<Card> getDeck() {
        return deck;
    }

    public void setDeck(Set<Card> deck) {
        this.deck = deck;
    }

    public void addDeck(CardHand cardHand) {
        deck.addAll(cardHand.getDeck());
    }

    public Boolean isCardPresent(Card card) {
        return deck.contains(card);
    }

    public void removeCard(Card card) {
        if(isCardPresent(card)){
            deck.remove(card);
        }
    }

    public Map<CardSuite, List<Card>> getCardHands() {
        Map<CardSuite, List<Card>> cardMap = new HashMap<>();

        for(CardSuite cardSuite: CardSuite.getAllSuites()) {
            cardMap.put(cardSuite, new ArrayList<Card>());
        }

        for(Card card: deck){
            List<Card> cardNumbers = cardMap.get(card.getCardSuite());
            cardNumbers.add(card);
        }
        return cardMap;
    }
}
