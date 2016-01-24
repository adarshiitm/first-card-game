package com.adarsh.resources;

import com.adarsh.utils.GuiceInjector;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by adarsh.sharma on 23/01/16.
 */

@Path("/card-game")
@Produces(MediaType.APPLICATION_JSON)
public class CardGameResource {
    private static final Logger logger = LoggerFactory.getLogger(CardGameResource.class);

    public CardGameResource() {
        GuiceInjector.getInjector().injectMembers(this);
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response handleBulkRequest() {
//        return Response.status(e.getStatus()).entity(reachOutBulkResponse).build();
        return Response.ok().entity("success").build();
    }

}


