package com.totallytot;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ToolUtils {
    private static String jiraUsername, jiraPassword, crowdApplicationUser, crowdApplicationPassword, baseURL;
    public static String filePath;
    static final String VERSION = "REST API Tool for Atlassian apps v.1.1";

    public static String getBaseURL() {
        return baseURL;
    }

    static void setBaseURL(String baseURL) {
        ToolUtils.baseURL = baseURL;
    }

    public static String getJiraUsername() {
        return jiraUsername;
    }

    public static String getJiraPassword() {
        return jiraPassword;
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
        return encodeCredentials(jiraUsername, jiraPassword);
    }

    public static String getCrowdBasicAuth() {
        return encodeCredentials(crowdApplicationUser, crowdApplicationPassword);
    }

    private static String encodeCredentials(String username, String password) {
        byte[] credentials = (username + ':' + password).getBytes();
        return "Basic " + new String(Base64.encodeBase64(credentials));
    }

    public static void print(Object text) {
        System.out.println(text);
    }

    static void loadProperties() {
        Properties prop = new Properties();
        try {
            prop.load(Tool.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            ToolUtils.print("Unable to find config.properties");
            e.printStackTrace();
        }
        jiraUsername = prop.getProperty("jira.username");
        jiraPassword = prop.getProperty("jira.password");
        crowdApplicationUser = prop.getProperty("crowd.application.user");
        crowdApplicationPassword = prop.getProperty("crowd.application.password");
        filePath = prop.getProperty("input.file.location");
    }

    static void showHelp() {
        ToolUtils.print("-help - shows help (current message);");
        ToolUtils.print("-version - shows actual version of the tool;");
        ToolUtils.print("[crowd] [Base URL] -cu - creates active users based on input data " + filePath
                + ". Input format: username,password,first,last,email. One user per line.");
        ToolUtils.print("[crowd] [Base URL] -ug [Group] - updates group membership based on input data " + filePath
                + ". Input format: username. One user per line.");
        ToolUtils.print("[jira] [Base URL] -r - generates resolutions report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -it - generates issue types report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -cf - generates custom fields report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -ws - generates workflow statuses report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -rw - removes workflow schemes based on input data " + filePath
                + ". Input format: Workflow scheme ids. One id per line.");
    }
}