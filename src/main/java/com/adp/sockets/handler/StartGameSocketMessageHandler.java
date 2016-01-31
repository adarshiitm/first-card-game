package com.adp.sockets.handler;

import com.adp.models.GameData;
import com.adp.sockets.models.GameSocketMessage;
import com.adp.sockets.models.SocketMessage;
import com.adp.sockets.models.enums.MessageType;
import com.adp.utils.MyObjectMapper;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adarsh.sharma on 31/01/16.
 */
public class StartGameSocketMessageHandler extends SocketMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartGameSocketMessageHandler.class);

    @Override
    public void handleMessage(String uuid, SocketMessage receivedSocketMessage) throws ApiException {
        SocketMessage socketMessage = new SocketMessage();
        try {
            String data = receivedSocketMessage.getData();
            GameSocketMessage gameSocketMessage =
                    MyObjectMapper.getClassObjectByDefaultMapper(data, GameSocketMessage.class);
            GameData gameData = gameService.startGame(uuid, gameSocketMessage.getGameType(),
                                                      gameSocketMessage.getPlayerId());
            socketMessage.setMessageType(MessageType.GAME_STARTED);
            socketMessage.setData(MyObjectMapper.getDefaultJson(gameData));
            sendMessage(uuid, MyObjectMapper.getDefaultJson(socketMessage));
        } catch (ApiException e) {
            logger.error("Failed to handle message {} with uuid: {}", receivedSocketMessage.getData(), uuid);
            socketMessage.setMessageType(MessageType.START_GAME_FAILED);
            socketMessage.setData(null);
            socketMessage.setResponseErrorMsg(ResponseErrorMsg.RUNTIME_ERROR);
            sendMessageWithNoException(uuid, MyObjectMapper.getDefaultJson(socketMessage));
        }
    }
}
