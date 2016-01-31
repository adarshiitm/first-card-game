package com.adp.sockets.models;

import com.adp.models.GameType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by adarsh.sharma on 31/01/16.
 */
public class GameSocketMessage {
    @JsonProperty("game_type")
    private GameType gameType;

    @JsonProperty("player_id")
    private String playerId;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
