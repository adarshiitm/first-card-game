package com.adp.bootstrap;

import com.adp.config.CardGameConfiguration;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameManage implements Managed {

    private static final Logger logger = LoggerFactory.getLogger(CardGameManage.class);
    private CardGameConfiguration config;


    @Inject
    public CardGameManage(CardGameConfiguration config) {
        this.config = config;
    }

    @Override
    public void start() throws Exception {

//        try {
//            logger.info("<<<<<<<<<<============= Starting ReachOut ===========>>>>>>>>>>");
//            logger.info("Initializing all the prerequisites");
//        } catch (ApiException e) {
//            logger.error("There is some problem while starting the manage service {}", e.getMessage(), e);
//            Thread.sleep(2000); /* To print the upper error message in the logs */
//            System.exit(-1);
//        }
    }

    @Override
    public void stop() throws Exception {
        logger.info(">>>>>>>>>>> Gracefully Shutting Down ReachOut <<<<<<<<<<<<<<");
    }
}
