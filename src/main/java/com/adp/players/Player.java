package com.adp.players;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public class Player {
    private String playerId;
    private String uuid;

    public Player(String playerId, String uuid) {
        this.playerId = playerId;
        this.uuid = uuid;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
