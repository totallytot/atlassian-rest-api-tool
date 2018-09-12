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

    static JiraRestClient getJiraRestClient(String jiraBaseUrl, String username, String password)  {
        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        URI jiraServerUri = null;
        try {
            jiraServerUri = new URI(jiraBaseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final BasicHttpAuthenticationHandler basicHttpAuthenticationHandler = new BasicHttpAuthenticationHandler(username, password);
        return factory.create(jiraServerUri, basicHttpAuthenticationHandler);
    }

    static void showHelp() {
        ToolUtils.print("-help - shows help (current message);");
        ToolUtils.print("-version - shows actual version of the tool;");
        ToolUtils.print("[crowd] [Base URL] -cu - creates active users based on input data " + Tool.FILENAME
                + ". Input format: username,password,first,last,email. One user per line.");
        ToolUtils.print("[crowd] [Base URL] -ug [Group] - updates group membership based on input data " + Tool.FILENAME
                + ". Input format: username. One user per line.");
        ToolUtils.print("[jira] [Base URL] -r - generates resolutions report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -it - generates issue types report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -cf - generates custom fields report. The output is xlsx file located in the" +
                " same dir as jar file.");
        ToolUtils.print("[jira] [Base URL] -ws - generates workflow statuses report. The output is xlsx file located in the" +
                " same dir as jar file.");
    }
}