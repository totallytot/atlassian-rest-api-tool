package com.totallytot;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.apache.commons.codec.binary.Base64;

import java.net.URI;
import java.net.URISyntaxException;

public class ToolUtils {

    static String encodeCredentials(String username, String password) {
        byte[] credentials = (username + ':' + password).getBytes();
        return "Basic " + new String(Base64.encodeBase64(credentials));
    }

    public static void print(Object text) {
        System.out.println(text);
    }

    static JiraRestClient getJiraRestClient(String instance, String username, String password) throws URISyntaxException {
        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final URI jiraServerUri = new URI(instance);
        final BasicHttpAuthenticationHandler basicHttpAuthenticationHandler = new BasicHttpAuthenticationHandler(username, password);
        return factory.create(jiraServerUri, basicHttpAuthenticationHandler);
    }

    static void showHelp() {
        ToolUtils.print("-help - shows help (current message);");
        ToolUtils.print("-version - shows actual version of the tool;");
        ToolUtils.print("[crowd] [Base URL] -cu - creates active users based on input data " + Tool.FILENAME + ". Input format: username,password,first,last,email. One user per line.");
        ToolUtils.print("[crowd] [Base URL] -ug [Group] - updates group membership based on input data " + Tool.FILENAME + ". Input format: username. One user per line.");
    }
}
