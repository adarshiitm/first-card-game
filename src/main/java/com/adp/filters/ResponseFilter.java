package com.adp.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Created by adarsh.sharma on 23/01/16.
 */

public class ResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        String requestHeader = containerRequestContext.getHeaders().getFirst("Access-Control-Request-Headers");
        if (null != requestHeader) {
            logger.info("setting access control headers.");
            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", requestHeader);
        }
        logger.info("Returning response with status {} ", containerResponseContext.getStatus());
    }
}


