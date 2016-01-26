package com.adp.utils;

import com.google.inject.Injector;

/**
 * Created by adarsh.sharma on 23/01/16.
 */


public class GuiceInjector {
    private static Injector injector = null;

    public static void assignInjector(Injector givenInjector) {
        if (injector == null)
            synchronized (GuiceInjector.class) {
                if (injector == null) {
                    injector = givenInjector;
                }
            }
        else
            throw new IllegalStateException("Injector instance was already assigned.");
    }

    public static Injector getInjector() {
        if (injector == null) {
            synchronized (GuiceInjector.class) {
                if (injector == null)
                    throw new IllegalStateException("Injector instance is not assigned. Please assign first");

            }
        }
        return injector;
    }
}
