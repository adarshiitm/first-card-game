package com.adp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public class GameData {
    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("game_type")
    private GameType gameType;

    @JsonProperty("player_count")
    private Integer playerCount;

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

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }
}
