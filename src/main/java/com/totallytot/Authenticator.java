package com.totallytot;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.apache.commons.codec.binary.Base64;

import java.net.URI;
import java.net.URISyntaxException;

public class Authenticator {
    private static String jiraUsername, jiraPassword, jiraCloudUsername, jiraCloudPassword,
            crowdApplicationUser, crowdApplicationPassword, baseURL;

    public static String getJiraUsername() {
        return jiraUsername;
    }

    public static void setJiraUsername(String jiraUsername) {
        Authenticator.jiraUsername = jiraUsername;
    }

    public static String getJiraPassword() {
        return jiraPassword;
    }

    public static void setJiraPassword(String jiraPassword) {
        Authenticator.jiraPassword = jiraPassword;
    }

    public static String getJiraCloudUsername() {
        return jiraCloudUsername;
    }

    public static void setJiraCloudUsername(String jiraCloudUsername) {
        Authenticator.jiraCloudUsername = jiraCloudUsername;
    }

    public static String getJiraCloudPassword() {
        return jiraCloudPassword;
    }

    public static void setJiraCloudPassword(String jiraCloudPassword) {
        Authenticator.jiraCloudPassword = jiraCloudPassword;
    }

    public static String getCrowdApplicationUser() {
        return crowdApplicationUser;
    }

    public static void setCrowdApplicationUser(String crowdApplicationUser) {
        Authenticator.crowdApplicationUser = crowdApplicationUser;
    }

    public static String getCrowdApplicationPassword() {
        return crowdApplicationPassword;
    }

    public static void setCrowdApplicationPassword(String crowdApplicationPassword) {
        Authenticator.crowdApplicationPassword = crowdApplicationPassword;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static void setBaseURL(String baseURL) {
        Authenticator.baseURL = baseURL;
    }

    private static String encodeBase64Credentials(String username, String password) {
        byte[] credentials = (username + ':' + password).getBytes();
        return "Basic " + new String(Base64.encodeBase64(credentials));
    }

    public static JiraRestClient getJiraRestClient() {
        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        URI jiraServerUri = null;
        try {
            jiraServerUri = new URI(baseURL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final BasicHttpAuthenticationHandler basicHttpAuthenticationHandler = new BasicHttpAuthenticationHandler(jiraUsername, jiraPassword);
        return factory.create(jiraServerUri, basicHttpAuthenticationHandler);
    }

    public static String getJiraBasicAuth() {
        return encodeBase64Credentials(jiraUsername, jiraPassword);
    }

    public static String getCrowdBasicAuth() {
        return encodeBase64Credentials(crowdApplicationUser, crowdApplicationPassword);
    }

    public static String getJiraCloudBasicAuth() {
        return encodeBase64Credentials(jiraCloudUsername, jiraCloudPassword);
    }
}