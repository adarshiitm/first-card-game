package com.adp.cards;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class Card {
    private CardNumber cardNumber;
    private CardSuite cardSuite;
    static Card foldedCard;
    static Card JokerCard;

    static {
        foldedCard = new Card(CardNumber.FOLDED, CardSuite.NO_SUITE);
        JokerCard = new Card(CardNumber.JOKER_1, CardSuite.NO_SUITE);
    }

    public Card(CardNumber cardNumber, CardSuite cardSuite) {
        this.cardNumber = cardNumber;
        this.cardSuite = cardSuite;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public CardSuite getCardSuite() {
        return cardSuite;
    }

    public CardColor getCardColor() {
        return cardSuite.getCardColor();
    }

    public static Card getFoldedCard() {
        return foldedCard;
    }

    public static Card getJokerCard() {
        return JokerCard;
    }

}
