package com.adp.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameConfiguration extends Configuration {

    public DatabaseConfiguration getDatabase() {
        return database;
    }

    @Valid
    @NotNull
    @JsonProperty("database")
    public DatabaseConfiguration database = new DatabaseConfiguration();

}
