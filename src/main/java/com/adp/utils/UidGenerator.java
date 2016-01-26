package com.adp.utils;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by adarsh.sharma on 12/05/15.
 */
@Singleton
public class UidGenerator {
    private static final Integer MAX_LENGTH = 32;
    private static final Logger logger = LoggerFactory.getLogger(UidGenerator.class);

    public String generateUid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, MAX_LENGTH);
    }

}
