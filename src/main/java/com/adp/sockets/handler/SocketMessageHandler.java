package com.adp.sockets.handler;

import com.adp.service.GameService;
import com.adp.sockets.models.SocketMessage;
import com.adp.sockets.utils.SocketManager;
import com.adp.utils.GuiceInjector;
import com.adp.utils.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adarsh.sharma on 31/01/16.
 */
public abstract class SocketMessageHandler {
    protected GameService gameService;
    protected SocketManager socketManager;

    private static final Logger logger = LoggerFactory.getLogger(SocketMessageHandler.class);

    public SocketMessageHandler() {
        this.gameService = GuiceInjector.getInjector().getInstance(GameService.class);
        this.socketManager = GuiceInjector.getInjector().getInstance(SocketManager.class);
    }

    public abstract void handleMessage(String uuid, SocketMessage receivedSocketMessage) throws ApiException;

    public void sendMessage(String uuid, String msg) throws ApiException {
        socketManager.sendMessage(uuid, msg);
    }

    public void sendMessageWithNoException(String uuid, String msg) {
        try {
            socketManager.sendMessage(uuid, msg);
        } catch (ApiException e) {
            logger.error("Failed to send msg {} to socket {}", msg, uuid);
        }
    }
}
