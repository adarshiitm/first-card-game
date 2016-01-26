package com.adp.resources;

import com.adp.sockets.SocketManager;
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
public class ChatService extends OnMessage<String> {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Override
    public void onMessage(AtmosphereResponse response, String message) throws IOException {
        logger.info("got message: {}", message);
    }

    @Override
    public void onOpen(AtmosphereResource resource) throws IOException {
        logger.info("onOpen: got {}", resource.uuid());
        SocketManager.addSocket(resource);
    }

}
