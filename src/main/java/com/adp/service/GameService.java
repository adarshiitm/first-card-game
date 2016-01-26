package com.adp.service;

import com.adp.games.Game;
import com.adp.games.GameFactory;
import com.adp.cards.CardHand;
import com.adp.models.GameData;
import com.adp.models.GameType;
import com.adp.utils.exceptions.ApiException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
@Singleton
public class GameService {
    private Map<String, Game> gameIdToGameMap;

    @Inject
    private GameFactory gameFactory;

    public GameService() {
        this.gameIdToGameMap = new HashMap<>();
    }

    public GameData startGame(GameType gameType, String playerId) throws ApiException {
        Game game = gameFactory.getGameByType(gameType);
        game.addPlayer(playerId);
        gameIdToGameMap.put(game.getGameData().getGameId(), game);
        return game.getGameData();
    }

    public CardHand newDeal(String gameId, String playerId) {
        return null;
    }

    public CardHand remainingCards(String gameId, String playerId) {
        return null;
    }
}
