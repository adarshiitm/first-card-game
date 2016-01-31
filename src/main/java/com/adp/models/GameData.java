package com.adp.models;

import com.adp.games.Game;
import com.adp.players.Player;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public class GameData {
    @JsonProperty("player")
    private Player player;

    @JsonProperty("game_type")
    private GameType gameType;

    @JsonProperty("total_player_count")
    private Integer totalPlayerCount;

    @JsonProperty("current_player_count")
    private Integer currentPlayerCount;

    public GameData() {
    }

    public GameData(Player player, Game game) {
        this.player = player;
        this.gameType = game.getGameType();
        this.totalPlayerCount = game.getTotalPlayerCount();
        this.currentPlayerCount = game.getPlayers().size();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public Integer getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public void setCurrentPlayerCount(Integer currentPlayerCount) {
        this.currentPlayerCount = currentPlayerCount;
    }
}
