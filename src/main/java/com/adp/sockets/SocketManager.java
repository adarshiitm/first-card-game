package com.adp.sockets;

import org.apache.commons.collections4.map.HashedMap;
import org.atmosphere.cpr.AtmosphereResource;

import java.io.IOException;
import java.util.Map;

/**
 * Created by adarsh.sharma on 26/01/16.
 */
public class SocketManager {
    private static Map<String, AtmosphereResource> uidToSocketMap = new HashedMap<>();

    public static void addSocket(AtmosphereResource atmosphereResource) {
        uidToSocketMap.put(atmosphereResource.uuid(), atmosphereResource);
    }

    public static void sendMessage(String msg) throws IOException {
        for(AtmosphereResource atmosphereResource: uidToSocketMap.values()) {
            atmosphereResource.getResponse().getWriter().write(msg);
        }
    }
}
