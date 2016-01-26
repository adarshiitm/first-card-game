package com.adp.bootstrap;

import com.adp.config.CardGameConfiguration;
import com.google.inject.AbstractModule;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.validation.Validator;


/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameModule extends AbstractModule {

    private HibernateBundle<CardGameConfiguration> bundle;
    private CardGameConfiguration config;
    private Validator validator;

    public CardGameModule(HibernateBundle<CardGameConfiguration> bundle,
                          CardGameConfiguration config,
                          Validator validator) {
        this.bundle = bundle;
        this.config = config;
        this.validator = validator;
    }

    @Override
    protected void configure() {
        bind(HibernateBundle.class).toInstance(bundle);
        bind(CardGameConfiguration.class).toInstance(config);
        bind(Validator.class).toInstance(validator);
    }

//    @Provides
//    @Singleton
//    public PriorityService providePriorityService() {
//        return new PriorityService();
//    }


}
