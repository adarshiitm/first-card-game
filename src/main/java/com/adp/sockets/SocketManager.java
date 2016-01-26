package com.adp.sockets;

import org.atmosphere.cpr.AtmosphereResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adarsh.sharma on 26/01/16.
 */
public class SocketManager {
    private static List<AtmosphereResponse> sockets = new ArrayList<>();

    public static void addSocket(AtmosphereResponse socket) {
        sockets.add(socket);
    }

    public static void sendMessage(String msg) throws IOException {
        for(AtmosphereResponse socket: sockets) {
            socket.getWriter().write(msg);
        }
    }
}
