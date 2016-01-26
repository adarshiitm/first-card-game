package com.adp.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class ResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        Response.ResponseBuilder response = Response.fromResponse(containerResponse.getResponse());
        response.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        String requestHeader = containerRequest.getHeaderValue("Access-Control-Request-Headers");
        if (null != requestHeader) {
            logger.info("setting access control headers.");
            response.header("Access-Control-Allow-Headers", requestHeader);
        }
        containerResponse.setResponse(response.build());
        logger.info("Returning response");
        return containerResponse;
    }
}
/**
 * Created by adarsh.sharma on 23/01/16.
 */

//public class ResponseFilter implements ContainerResponseFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
//
//    @Override
//    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
//        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
//        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
//        String requestHeader = containerRequestContext.getHeaders().getFirst("Access-Control-Request-Headers");
//        if (null != requestHeader) {
//            logger.info("setting access control headers.");
//            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", requestHeader);
//        }
//        logger.info("Returning response with status {} ", containerResponseContext.getStatus());
//    }
//}



