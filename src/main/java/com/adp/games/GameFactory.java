package com.adp.games;

import com.adp.models.GameType;
import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;

import javax.ws.rs.core.Response;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public class GameFactory {
    public Game getGameByType(GameType gameType) throws ApiException {
        switch (gameType){
            case TEEN_DO_PAANCH:
                return new TeenDoPaanchGame();

            default:
                throw new ApiException(Response.Status.BAD_REQUEST, ResponseErrorMsg.RUNTIME_ERROR, "invalid game type");
        }
    }
}
