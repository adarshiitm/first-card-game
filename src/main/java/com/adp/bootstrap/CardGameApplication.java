package com.adp.bootstrap;

import com.adp.config.CardGameConfiguration;
import com.adp.filters.RequestFilter;
import com.adp.filters.ResponseFilter;
import com.adp.resources.CardGameResource;
import com.adp.resources.atmosphere.SocketResource;
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
        environment.jersey().register(injector.getInstance(SocketResource.class));

//        CoreRotationManagementTask managementTask = new CoreRotationManagementTask(config.getRotationManagementConfig());
//        environment.admin().addTask(managementTask);
//        environment.healthChecks().register("app-health", new AppInRotationHealthCheck(managementTask));

        environment.jersey().register(injector.getInstance(RequestFilter.class));
        environment.jersey().register(injector.getInstance(ResponseFilter.class));

//        initializeAtmosphere(environment);

        JmxReporter.forRegistry(environment.metrics()).build().start();
        JmxReporter.forRegistry(metricRegistry).build().start();
    }

//    void initializeAtmosphere(Environment environment) {
//
//        FilterBuilder config = environment.addFilter(CrossOriginFilter.class, "/socket");
//        config.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
//        FilterBuilder config
//
//        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
//        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.adp.resources.atmosphere");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.maxIdleTime", "120000");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.interceptor.HeartbeatInterceptor.heartbeatFrequencyInSeconds", "60");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.AsyncSupport.maxInactiveActivity", "120000");
//        environment.getApplicationContext().addServlet("org.atmosphere.cpr.AtmosphereServlet", "/socket/*");
//    }
}
