package com.adp.bootstrap;

import com.adp.config.CardGameConfiguration;
import com.adp.filters.RequestFilter;
import com.adp.filters.ResponseFilter;
import com.adp.resources.CardGameResource;
import com.adp.resources.atmosphere.SocketResource;
import com.adp.utils.GuiceInjector;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameApplication extends Service<CardGameConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(CardGameApplication.class);

    public static void main(String[] args) throws Exception {
        new CardGameApplication().run(args);
    }

    private final HibernateBundle<CardGameConfiguration> hibernate = new HibernateBundle<CardGameConfiguration>(
        CardGameConfiguration.class
    ) {

        @Override
        public DatabaseConfiguration getDatabaseConfiguration(CardGameConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    @Override
    public void initialize(Bootstrap<CardGameConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(CardGameConfiguration config, Environment environment) throws Exception {
        Validator validator = environment.getValidator();
        Injector injector = Guice.createInjector(new CardGameModule(hibernate, config, validator));
        GuiceInjector.assignInjector(injector);

        environment.manage(injector.getInstance(CardGameManage.class));
        environment.addResource(injector.getInstance(CardGameResource.class));
        environment.addResource(injector.getInstance(SocketResource.class));


//        CoreRotationManagementTask managementTask = new CoreRotationManagementTask(config.getRotationManagementConfig());
//        environment.admin().addTask(managementTask);
//        environment.healthChecks().register("app-health", new AppInRotationHealthCheck(managementTask));

        environment.getJerseyResourceConfig().getContainerRequestFilters().add(new RequestFilter());
        environment.getJerseyResourceConfig().getContainerResponseFilters().add(new ResponseFilter());

//        initializeAtmosphere(environment);
//        initializeAtmosphere2(environment);
    }

//    void initializeAtmosphere(Environment environment) {
//
//        FilterBuilder config = environment.addFilter(CrossOriginFilter.class, "/socket");
//        config.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
//
//        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
//        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.adp.resources.atmosphere");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.maxIdleTime", "120000");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.interceptor.HeartbeatInterceptor.heartbeatFrequencyInSeconds", "60");
//        atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.AsyncSupport.maxInactiveActivity", "120000");
//        environment.addServlet(atmosphereServlet, "/socket/*");
//
//    }

//    void initializeAtmosphere2(Environment environment) {
//        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
//        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "dk.cooldev.chatroom.resources.websocket");
//        atmosphereServlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
//        atmosphereServlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");
//
//        ServletRegistration.Dynamic servletHolder = environment.getServlets().addServlet("socket", atmosphereServlet);
//        servletHolder.addMapping("/socket/*");
//    }
}
