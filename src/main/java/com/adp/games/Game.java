package com.adp.games;

import com.adp.cards.Card;
import com.adp.cards.CardHand;
import com.adp.cards.CardUtils;
import com.adp.models.GameData;
import com.adp.models.GameType;
import com.adp.utils.UidGenerator;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.google.inject.Inject;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public abstract class Game {
    protected GameData gameData;
    protected CardHand deck;
    protected Set<String> players;
    protected Map<String, CardHand> playerToCardHand;

    @Inject
    private UidGenerator uidGenerator;

    public Game(Integer numberOfPlayers, GameType gameType) throws ApiException {
        this.gameData = new GameData();
        gameData.setGameId(uidGenerator.generateUid());
        gameData.setPlayerCount(numberOfPlayers);
        gameData.setGameType(gameType);
        this.deck = CardUtils.getDeckForGame(gameType);
        this.players = new HashSet<>();
        this.playerToCardHand = new HashMap<>();
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public CardHand getDeck() {
        return deck;
    }

    public void setDeck(CardHand deck) {
        this.deck = deck;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public void addPlayer(String playerId) {
        players.add(playerId);
    }

    public Map<String, CardHand> getPlayerToCardHand() {
        return playerToCardHand;
    }

    public void setPlayerToCardHand(Map<String, CardHand> playerToCardHand) {
        this.playerToCardHand = playerToCardHand;
    }

    public void addPlayerHand(String playerId, CardHand cardHand){
        if(!playerToCardHand.containsKey(playerId)) {
            playerToCardHand.put(playerId, cardHand);
        } else {
            CardHand existingCardHand = playerToCardHand.get(playerId);
            existingCardHand.addDeck(cardHand);
        }
    }

    public void removePlayerCard(String playerId, Card card) throws ApiException {
        if(playerToCardHand.containsKey(playerId)){
            CardHand cardHand = playerToCardHand.get(playerId);
            if(cardHand.isCardPresent(card)){
                cardHand.removeCard(card);
            } else {
                throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR, "invalid card");
            }
        } else {
            throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR, "invalid player");
        }
    }
}
