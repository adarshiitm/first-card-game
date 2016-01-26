package com.adp.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Created by adarsh.sharma on 23/01/16.
 */

public class ResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);


    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        String requestHeader = containerRequest.getRequestHeaders().getFirst("Access-Control-Request-Headers");

        if (null != requestHeader) {
            logger.info("setting access control headers.");
            containerResponse.getHttpHeaders().add("Access-Control-Allow-Headers", requestHeader);
        }
        logger.info("Returning response with status {} ", containerResponse.getStatus());
        return containerResponse;
    }
}


