package com.adarsh.bootstrap;

import com.adarsh.config.CardGameConfiguration;
import com.adarsh.filters.RequestFilter;
import com.adarsh.filters.ResponseFilter;
import com.adarsh.resources.CardGameResource;
import com.adarsh.utils.GuiceInjector;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameApplication extends Application<CardGameConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(CardGameApplication.class);

    public static void main(String[] args) throws Exception {
        new CardGameApplication().run(args);
    }

    private final HibernateBundle<CardGameConfiguration> hibernate = new HibernateBundle<CardGameConfiguration>(
        CardGameConfiguration.class
    ) {

        @Override
        public DataSourceFactory getDataSourceFactory(CardGameConfiguration cardGameConfiguration) {
            return cardGameConfiguration.getDatabase();
        }
    };

    @Override
    public void initialize(Bootstrap<CardGameConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(CardGameConfiguration config, Environment environment) throws Exception {
        Validator validator = environment.getValidator();
        MetricRegistry metricRegistry = new MetricRegistry();
        Injector injector = Guice.createInjector(new CardGameModule(hibernate, config, validator, metricRegistry));
        GuiceInjector.assignInjector(injector);

        environment.lifecycle().manage(injector.getInstance(CardGameManage.class));
        environment.jersey().register(injector.getInstance(CardGameResource.class));
//        environment.jersey().register(injector.getInstance(WSResource.class));
//        environment.jersey().register(injector.getInstance(CallBackResource.class));
//        environment.jersey().register(injector.getInstance(AgentMgrCallEventResource.class));
//        environment.jersey().register(injector.getInstance(RMQResource.class));

//        CoreRotationManagementTask managementTask = new CoreRotationManagementTask(config.getRotationManagementConfig());
//        environment.admin().addTask(managementTask);
//        environment.healthChecks().register("app-health", new AppInRotationHealthCheck(managementTask));

        environment.jersey().register(injector.getInstance(RequestFilter.class));
        environment.jersey().register(injector.getInstance(ResponseFilter.class));

        JmxReporter.forRegistry(environment.metrics()).build().start();
        JmxReporter.forRegistry(metricRegistry).build().start();
    }
}
