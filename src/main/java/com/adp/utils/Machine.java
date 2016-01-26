package com.adp.utils;

import com.google.inject.Singleton;

import java.net.InetAddress;

/**
 * Created by adarsh.sharma on 23/01/16.
 */

@Singleton
public class Machine {

    private String machineName;

    public String getName() {

        if (machineName == null) {
            try {
                machineName = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                machineName = "notFound";
            }
        }
        if(machineName.equals("notFound"))
        {
            return null;
        }
        return machineName;
    }

}

