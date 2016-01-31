package com.adp.service;

import com.adp.cards.CardHand;
import com.adp.games.Game;
import com.adp.games.GameFactory;
import com.adp.models.GameData;
import com.adp.models.GameType;
import com.adp.players.Player;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
@Singleton
public class GameService {
    private Map<String, Game> gameIdToGameMap;
    private Map<String, Game> gamePlayerIdToGameMap;
    private Map<GameType, List<Game>> gameTypeToOpenGamesMap;

    @Inject
    private GameFactory gameFactory;

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    public GameService() {
        this.gameIdToGameMap = new HashMap<>();
        this.gamePlayerIdToGameMap = new HashMap<>();
        this.gameTypeToOpenGamesMap = new HashMap<>();
    }

    public GameData startGame(String uuid, GameType gameType, String playerId) throws ApiException {
        Game game = gameFactory.getGameByType(gameType);
        Player player = new Player(playerId, uuid);
        game.addPlayer(player);
        gameIdToGameMap.put(game.getGameId(), game);
        gamePlayerIdToGameMap.put(uuid, game);
        addNewGame(gameType, game);
        return new GameData(player, game);
    }

    public synchronized Game joinGame(GameType gameType, Player player) throws ApiException {
        if (gameTypeToOpenGamesMap.containsKey(gameType)) {
            List<Game> openGames = gameTypeToOpenGamesMap.get(gameType);
            for (Game game : openGames) {
                if (game.getPlayers().size() < game.getTotalPlayerCount()) {
                    game.addPlayer(player);
                    gamePlayerIdToGameMap.put(player.getUuid(), game);
                    return game;
                }
            }
        }

        throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR, "no open games found");
    }

    public CardHand newDeal(String gameId, String playerId) {
        return null;
    }

    public CardHand remainingCards(String gameId, String playerId) {
        return null;
    }

    private synchronized void addNewGame(GameType gameType, Game game) {
        List<Game> openGames;
        if (gameTypeToOpenGamesMap.containsKey(gameType)) {
            openGames = gameTypeToOpenGamesMap.get(gameType);
        } else {
            openGames = new ArrayList<>();
            gameTypeToOpenGamesMap.put(gameType, openGames);
        }
        openGames.add(game);
    }

}
