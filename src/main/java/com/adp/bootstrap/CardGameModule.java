package com.adp.bootstrap;

import com.adp.config.CardGameConfiguration;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.AbstractModule;
import io.dropwizard.hibernate.HibernateBundle;

import javax.validation.Validator;


/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameModule extends AbstractModule {

    private HibernateBundle<CardGameConfiguration> bundle;
    private CardGameConfiguration config;
    private Validator validator;
    private MetricRegistry metricRegistry;

    public CardGameModule(HibernateBundle<CardGameConfiguration> bundle,
                          CardGameConfiguration config,
                          Validator validator,
                          MetricRegistry metricRegistry) {
        this.bundle = bundle;
        this.config = config;
        this.validator = validator;
        this.metricRegistry = metricRegistry;
    }

    @Override
    protected void configure() {
        bind(HibernateBundle.class).toInstance(bundle);
        bind(CardGameConfiguration.class).toInstance(config);
        bind(Validator.class).toInstance(validator);
        bind(MetricRegistry.class).toInstance(metricRegistry);
    }

//    @Provides
//    @Singleton
//    public PriorityService providePriorityService() {
//        return new PriorityService();
//    }


}
