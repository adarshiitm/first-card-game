package com.adp.cards;

import com.adp.models.GameType;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.google.inject.Singleton;

import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
@Singleton
public class CardUtils {
    private static Set<Card> allCards;
    private static List<CardSuite> allSuites = CardSuite.getAllSuites();
    private static List<CardNumber> allNumbers = CardNumber.getAllNumbers();

    static {
        allCards = new HashSet<Card>();
        for (CardSuite cardSuite : allSuites) {
            for (CardNumber cardNumber : allNumbers) {
                allCards.add(new Card(cardNumber, cardSuite));
            }
        }
    }

    public static CardHand getAllCards() {
        return new CardHand(allCards);
    }

    public static CardHand getDeckForGame(GameType gameType) throws ApiException {
        Set<Card> deck = new HashSet<Card>();

        switch (gameType) {
            case TEEN_DO_PAANCH: {
                for (CardSuite cardSuite : allSuites) {
                    for (CardNumber cardNumber : allNumbers) {
                        if (cardNumber.ordinal() > 6 || cardNumber.ordinal() == 1) {
                            if ((cardNumber.ordinal() != 7 ||
                                    cardSuite == CardSuite.SPADE ||
                                    cardSuite == CardSuite.HEART)) {
                                deck.add(new Card(cardNumber, cardSuite));
                            }
                        }
                    }
                }
            }
            break;

            default:
                throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR, "invalid games");
        }

        return new CardHand(deck);
    }

}
