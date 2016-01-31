package com.adp.sockets.service;

import com.adp.sockets.handler.SocketMessageHandlerFactory;
import com.adp.sockets.utils.SocketManager;
import com.adp.utils.GuiceInjector;
import com.adp.utils.exceptions.ApiException;
import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.handler.OnMessage;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import java.io.IOException;

/**
 * Created by adarsh.sharma on 26/01/16.
 */
@Path("/")
@AtmosphereHandlerService(path = "/chat",
        broadcasterCache = UUIDBroadcasterCache.class,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class,
                BroadcastOnPostAtmosphereInterceptor.class,
                TrackMessageSizeInterceptor.class,
                HeartbeatInterceptor.class
        })
public class GameSocketService extends OnMessage<String> {
    private SocketManager socketManager;
    private static final Logger logger = LoggerFactory.getLogger(GameSocketService.class);

    public GameSocketService() {
        this.socketManager = GuiceInjector.getInjector().getInstance(SocketManager.class);
    }

    @Override
    public void onMessage(AtmosphereResponse response, String message) {
        logger.info("got message: {}", message);
        try {
            SocketMessageHandlerFactory.handleMessage(response.uuid(), message);
        } catch (ApiException e) {
            logger.error("onMessage to deserialize the data {}", message);
        }
    }

    @Override
    public void onOpen(AtmosphereResource resource) throws IOException {
        logger.info("onOpen: got {}", resource.uuid());
        socketManager.addSocket(resource);
    }

    @Override
    public void onDisconnect(AtmosphereResponse response) throws IOException {
        logger.info("onOpen: got {}", response.uuid());
        socketManager.removeSocket(response.uuid());
    }

}
