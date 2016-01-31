package com.adp.bootstrap;

import com.adp.config.CardGameConfiguration;
import com.adp.filters.RequestFilter;
import com.adp.filters.ResponseFilter;
import com.adp.resources.CardGameTestResource;
import com.adp.utils.GuiceInjector;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRegistration;
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
        environment.jersey().register(injector.getInstance(CardGameTestResource.class));

//        CoreRotationManagementTask managementTask = new CoreRotationManagementTask(config.getRotationManagementConfig());
//        environment.admin().addTask(managementTask);
//        environment.healthChecks().register("app-health", new AppInRotationHealthCheck(managementTask));

        environment.jersey().register(injector.getInstance(RequestFilter.class));
        environment.jersey().register(injector.getInstance(ResponseFilter.class));

        initializeAtmosphere(environment);

        JmxReporter.forRegistry(environment.metrics()).build().start();
        JmxReporter.forRegistry(metricRegistry).build().start();
    }


    void initializeAtmosphere(Environment environment) {
        AtmosphereServlet servlet = new AtmosphereServlet();
        servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "dk.cooldev.chatroom.resources.websocket");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");
        servlet.framework().addInitParameter(ApplicationConfig.MAX_INACTIVE, "120000");

        ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("Chat", servlet);
        servletHolder.addMapping("/chat/*");
    }
}
