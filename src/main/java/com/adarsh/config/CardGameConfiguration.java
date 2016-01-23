package com.adarsh.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class CardGameConfiguration extends Configuration{

    @Valid
    @JsonProperty("database")
    private DataSourceFactory database;

    @JsonProperty
    public DataSourceFactory getDatabase() {
        return database;
    }

}
