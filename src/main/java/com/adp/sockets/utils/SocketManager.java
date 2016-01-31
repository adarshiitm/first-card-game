package com.adp.sockets.utils;

import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.google.inject.Singleton;
import org.apache.commons.collections4.map.HashedMap;
import org.atmosphere.cpr.AtmosphereResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

/**
 * Created by adarsh.sharma on 26/01/16.
 */
@Singleton
public class SocketManager {
    private static Map<String, AtmosphereResource> uuidToSocketMap = new HashedMap<>();

    private static final Logger logger = LoggerFactory.getLogger(SocketManager.class);

    public void addSocket(AtmosphereResource atmosphereResource) {
        uuidToSocketMap.put(atmosphereResource.uuid(), atmosphereResource);
    }

    public void removeSocket(String uuid) {
        if(uuidToSocketMap.containsKey(uuid)){
            uuidToSocketMap.remove(uuid);
        }
    }

    public void sendMessage(String uuid, String msg) throws ApiException {
        if(uuidToSocketMap.containsKey(uuid)) {
            AtmosphereResource atmosphereResource = uuidToSocketMap.get(uuid);
            try {
                atmosphereResource.getResponse().getWriter().write(msg);
            } catch (IOException e) {
                logger.error("failed to send message to socket: {} with error: {} exception: {}", uuid, e.getMessage(), e);
                throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR,
                        "failed to send message to socket " + uuid);
            }
        }
    }

    public void sendMessage(String msg) throws IOException {
        for(AtmosphereResource atmosphereResource: uuidToSocketMap.values()) {
            atmosphereResource.getResponse().getWriter().write(msg);
        }
    }
}
