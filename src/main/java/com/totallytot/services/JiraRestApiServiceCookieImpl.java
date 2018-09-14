package com.totallytot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.totallytot.ToolUtils;

import java.io.IOException;
import java.io.StringReader;

public class JiraRestApiServiceCookieImpl implements RestApiService{

    public String getCookie() {
        String requestBody = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", ToolUtils.getJiraUsername(),
                ToolUtils.getJiraPassword());
        String JsonResponseBody = sendRequestAndGetBody("POST", "/rest/auth/1/session",
                ToolUtils.getJiraBasicAuth(), requestBody);
        /*
          {"session":{"name":"JSESSIONID","value":"5507ED57E446AE096FB98EDA810D99FC"},
          "loginInfo":{"loginCount":48,"previousLoginTime":"2018-09-14T01:05:04.174+0300"}}
         */
        //Read JSON like DOM Parser. Create root node from JSON string.
        JsonNode rootNode = null;
        try {
            rootNode = new ObjectMapper().readTree(new StringReader(JsonResponseBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get session element in the root node.
        assert rootNode != null;
        JsonNode innerNode = rootNode.get("session");
        //Get an element in that node as string.
        String sessionName = innerNode.get("name").asText();
        String sessionValue = innerNode.path("value").asText();
        return sessionName + ":" + sessionValue;
    }
}
