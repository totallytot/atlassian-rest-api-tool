package com.totallytot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.totallytot.Authenticator;
import com.totallytot.ToolUtils;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JiraRestApiServiceCookieImpl implements RestApiService{
    private String baseUrl;

    public JiraRestApiServiceCookieImpl() {
        this.baseUrl = Authenticator.getBaseURL();
    }

    public String getCookie() {
        String requestBody = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", Authenticator.getJiraUsername(),
                Authenticator.getJiraPassword());
        String JsonResponseBody = sendRequestAndGetBody(RequestType.POST, baseUrl + "rest/auth/1/session",
                Authenticator.getJiraBasicAuth(), requestBody);
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
        return sessionName + "=" + sessionValue;
    }

    public void activateWebSudo(String cookie) {
        URL url;
        OutputStreamWriter out;
        try {
            url = new URL(baseUrl + "/secure/admin/WebSudoAuthenticate.jspa");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Cookie", cookie);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("X-Atlassian-Token", "no-check");
            httpCon.setDoOutput(true);
            String data = "webSudoPassword: " + Authenticator.getJiraPassword();
            out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int sendDeleteRequest(String restApiUrl, String cookie) {
        return  sendRequestAndGetStatus(RequestType.DELETE, restApiUrl, true, cookie, null);
    }
}