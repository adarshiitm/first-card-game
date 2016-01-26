package com.adp.games;

import com.adp.models.GameType;
import com.adp.utils.exceptions.ApiException;

/**
 * Created by adarsh.sharma on 24/01/16.
 */
public class TeenDoPaanchGame extends Game {

    public TeenDoPaanchGame() throws ApiException {
        super(3, GameType.TEEN_DO_PAANCH);
    }
}
