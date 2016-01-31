package com.adp.resources;

import com.adp.cards.CardHand;
import com.adp.games.Game;
import com.adp.models.GameData;
import com.adp.models.GameType;
import com.adp.players.Player;
import com.adp.service.GameService;
import com.adp.sockets.models.SocketMessage;
import com.adp.sockets.models.enums.MessageType;
import com.adp.sockets.utils.SocketManager;
import com.adp.utils.GuiceInjector;
import com.adp.utils.MyObjectMapper;
import com.adp.utils.UidGenerator;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by adarsh.sharma on 23/01/16.
 */

@Path("/card-game")
@Produces(MediaType.APPLICATION_JSON)
public class CardGameTestResource {
    @Inject
    private GameService gameService;
    @Inject
    private SocketManager socketManager;
    @Inject
    private UidGenerator uidGenerator;

    private static final Logger logger = LoggerFactory.getLogger(CardGameTestResource.class);

    public CardGameTestResource() {
        GuiceInjector.getInjector().injectMembers(this);
    }

    @GET
    @Path("/start/{game_type}/{player_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response startGame(@PathParam("game_type") GameType gameType, @PathParam("player_id") String playerId) {
        SocketMessage socketMessage = new SocketMessage();
        try {
            GameData gameData = gameService.startGame(uidGenerator.generateUid(), gameType, playerId);
            socketMessage.setMessageType(MessageType.GAME_STARTED);
            socketMessage.setData(MyObjectMapper.getDefaultJson(gameData));
            return Response.ok().entity(socketMessage).build();
        } catch (Exception e) {
            socketMessage.setMessageType(MessageType.START_GAME_FAILED);
            socketMessage.setResponseErrorMsg(ResponseErrorMsg.RUNTIME_ERROR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(socketMessage).build();
        }
    }

    @GET
    @Path("/join/{game_type}/{player_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response joinGame(@PathParam("game_type") GameType gameType, @PathParam("player_id") String playerId) {
        SocketMessage socketMessage = new SocketMessage();
        try {
            String uuid = uidGenerator.generateUid();
            Player player = new Player(playerId, uuid);
            Game game = gameService.joinGame(gameType, player);
            GameData gameData = new GameData(player, game);
            socketMessage.setMessageType(MessageType.GAME_JOINED);
            socketMessage.setData(MyObjectMapper.getDefaultJson(gameData));
            return Response.ok().entity(socketMessage).build();
        } catch (Exception e) {
            socketMessage.setMessageType(MessageType.NO_OPEN_GAMES);
            socketMessage.setResponseErrorMsg(ResponseErrorMsg.RUNTIME_ERROR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(socketMessage).build();
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
    @Path("/test/{msg}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public Response test(@PathParam("msg") String msg) {
        try {
            socketManager.sendMessage(msg);
            return Response.ok().entity("success").build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("failed").build();
        }
    }
}


