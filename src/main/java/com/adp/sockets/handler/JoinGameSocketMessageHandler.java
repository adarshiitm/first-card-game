package com.adp.sockets.handler;

import com.adp.games.Game;
import com.adp.models.GameData;
import com.adp.players.Player;
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
public class JoinGameSocketMessageHandler extends SocketMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(JoinGameSocketMessageHandler.class);

    @Override
    public void handleMessage(String uuid, SocketMessage receivedSocketMessage) throws ApiException {
        SocketMessage socketMessage = new SocketMessage();
        try {
            String data = receivedSocketMessage.getData();
            GameSocketMessage gameSocketMessage =
                    MyObjectMapper.getClassObjectByDefaultMapper(data, GameSocketMessage.class);
            Player currentPlayer = new Player(gameSocketMessage.getPlayerId(), uuid);
            Game game = gameService.joinGame(gameSocketMessage.getGameType(), currentPlayer);

            socketMessage.setMessageType(MessageType.GAME_JOINED);
            socketMessage.setData(MyObjectMapper.getDefaultJson(new GameData(currentPlayer, game)));

            for(Player player: game.getPlayers()) {
                sendMessage(player.getUuid(), MyObjectMapper.getDefaultJson(socketMessage));
            }
        } catch (ApiException e) {
            logger.error("failed to join game for uuid: {}", uuid);
            socketMessage.setMessageType(MessageType.NO_OPEN_GAMES);
            socketMessage.setData(null);
            socketMessage.setResponseErrorMsg(ResponseErrorMsg.RUNTIME_ERROR);
            sendMessageWithNoException(uuid, MyObjectMapper.getDefaultJson(socketMessage));
        }
    }
}
