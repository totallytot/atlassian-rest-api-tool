package com.totallytot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class RestApiServiceUnirestImpl implements RestApiService {

    public int sendPutRequestAndGetStatus(String restApiUrl, String basicAuth, ObjectNode body) {
        //connect Jackson ObjectMapper to Unirest
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HttpResponse<String> response = null;
        try {
            response = Unirest.put(restApiUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", basicAuth)
                    .body(body)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return response.getStatus();
    }
}
