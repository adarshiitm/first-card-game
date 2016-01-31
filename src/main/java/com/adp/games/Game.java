package com.adp.games;

import com.adp.cards.Card;
import com.adp.cards.CardHand;
import com.adp.cards.CardUtils;
import com.adp.models.GameType;
import com.adp.players.Player;
import com.adp.utils.GuiceInjector;
import com.adp.utils.UidGenerator;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public abstract class Game {
    protected String gameId;
    private GameType gameType;
    private Integer totalPlayerCount;
    protected CardHand deck;
    protected Set<Player> players;
    protected Map<String, CardHand> playerToCardHand;

    private UidGenerator uidGenerator;

    public Game() {
        this.uidGenerator = GuiceInjector.getInjector().getInstance(UidGenerator.class);
    }

    public Game(Integer numberOfPlayers, GameType gameType) throws ApiException {
        this();
        this.gameId = uidGenerator.generateUid();
        this.gameType = gameType;
        this.totalPlayerCount = numberOfPlayers;
        this.deck = CardUtils.getDeckForGame(gameType);
        this.players = new HashSet<>();
        this.playerToCardHand = new HashMap<>();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getTotalPlayerCount() {
        return totalPlayerCount;
    }

    public void setTotalPlayerCount(Integer totalPlayerCount) {
        this.totalPlayerCount = totalPlayerCount;
    }

    public CardHand getDeck() {
        return deck;
    }

    public void setDeck(CardHand deck) {
        this.deck = deck;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
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
