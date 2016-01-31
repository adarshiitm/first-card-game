package com.adp.sockets.handler;

import com.adp.sockets.models.SocketMessage;
import com.adp.sockets.models.enums.MessageType;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static com.adp.utils.MyObjectMapper.*;

/**
 * Created by adarsh.sharma on 31/01/16.
 */
public class SocketMessageHandlerFactory {
    private static Map<MessageType, SocketMessageHandler> messageTypeToHandlerMap = new HashMap<>();

    static {
        registerHandler(MessageType.START_GAME, new StartGameSocketMessageHandler());
        registerHandler(MessageType.JOIN_GAME, new JoinGameSocketMessageHandler());
    }

    public static void registerHandler(MessageType messageType, SocketMessageHandler socketMessageHandler) {
        messageTypeToHandlerMap.put(messageType, socketMessageHandler);
    }

    public static SocketMessageHandler getSocketHandler(MessageType messageType) throws ApiException {
        if (messageTypeToHandlerMap.containsKey(messageType)) {
            return messageTypeToHandlerMap.get(messageType);
        }

        throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR, "wrong message type");
    }

    public static void handleMessage(String uuid, String message) throws ApiException {
        if (message != null) {
            SocketMessage socketMessage = getClassObjectByDefaultMapper(message, SocketMessage.class);
            SocketMessageHandler socketHandler =
                    SocketMessageHandlerFactory.getSocketHandler(socketMessage.getMessageType());
            socketHandler.handleMessage(uuid, socketMessage);
        }
    }
}
