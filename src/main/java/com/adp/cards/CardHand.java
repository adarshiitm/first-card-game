package com.adp.cards;

import java.util.Set;

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
}
