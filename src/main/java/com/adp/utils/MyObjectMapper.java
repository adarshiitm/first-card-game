package com.adp.utils;

import com.adp.utils.exceptions.ApiException;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by sanjay.rajput on 28/02/15.
 */

public class MyObjectMapper {
    private static ObjectMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(MyObjectMapper.class);
    public MyObjectMapper() {
    }

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        Hibernate4Module hbm = new Hibernate4Module();
        hbm.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
        mapper.registerModule(hbm);
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String getJsonString(Object object) throws ApiException {
        try{
            if (object != null)
                return mapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Failed to convert object to json", e);
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return null;
    }

    public static String getJsonStringNoException(Object object) {
        try{
            if (object != null)
                return mapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Failed to convert object to json", e);
        }
        return null;
    }

    public static String getDefaultJson(Object object) throws ApiException{
        try{
            if (object != null)
                return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Failed to convert object to json", e);
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return null;
    }

    public static <T> T getClassObject(String jsonString, Class<T> valueType, ObjectMapper mapper) throws ApiException{
        try{
            return mapper.readValue(jsonString, valueType);
        } catch (Exception e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public static <T> T getClassObjectByDefaultMapper(String jsonString, Class<T> valueType) throws ApiException{
        try{
            return mapper.readValue(jsonString, valueType);
        } catch (Exception e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public static Collection getCollectionObjectWithType(String response, Class valueType, Class modelClass) throws ApiException {
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(valueType, modelClass);
            return mapper.readValue(response, type);
        } catch (IOException e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, ResponseErrorMsg.RUNTIME_ERROR, e.getMessage());
        }
    }

    public static JsonNode valueToTree(Map<String, Object> map) {
        return mapper.valueToTree(map);
    }

    public static JsonNode readTree(String context) throws ApiException {
        try {
            return mapper.readTree(context);
        } catch (IOException e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, ResponseErrorMsg.RUNTIME_ERROR, e.getMessage());
        }
    }

    public static <T> T convertValue(JsonNode jsonNode, Class<T> valueType) {
        return mapper.convertValue(jsonNode, valueType);
    }

    public static <T> T treeToValue(JsonNode jsonNode, Class<T> valueType) throws JsonProcessingException {
        return mapper.treeToValue(jsonNode, valueType);
    }
}
