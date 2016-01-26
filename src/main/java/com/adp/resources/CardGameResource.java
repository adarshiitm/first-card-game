package com.adp.resources;

import com.adp.cards.CardHand;
import com.adp.models.GameData;
import com.adp.models.GameType;
import com.adp.service.GameService;
import com.adp.utils.GuiceInjector;
import com.adp.utils.exceptions.ApiException;
import com.google.inject.Inject;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import com.yammer.metrics.annotation.ExceptionMetered;
import com.yammer.metrics.annotation.Timed;
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
    @Inject
    private GameService gameService;

    private static final Logger logger = LoggerFactory.getLogger(CardGameResource.class);

    public CardGameResource() {
        GuiceInjector.getInjector().injectMembers(this);
    }

    @GET
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response startGame(@QueryParam("user_id") String playerId, @QueryParam("game_type") GameType gameType) {
        try {
            GameData gameData = gameService.startGame(gameType, playerId);
            return Response.ok().entity(gameData).build();
        } catch (ApiException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/new-deal/{game_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response newDeal(@QueryParam("user_id") String playerId, @PathParam("game_id") String gameId) {
        CardHand cardHand = gameService.newDeal(gameId, playerId);
        return Response.ok().entity(cardHand).build();
    }

    @GET
    @Path("/remaining-cards/{game_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response remainingCards(@QueryParam("user_id") String playerId, @PathParam("game_id") String gameId) {
        CardHand cardHand = gameService.remainingCards(gameId, playerId);
        return Response.ok().entity(cardHand).build();
    }


    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response test() {
        return Response.ok().entity("success").build();
    }
}


