package com.totallytot.services.jira;

import com.totallytot.Authenticator;
import com.totallytot.ToolUtils;
import com.totallytot.services.FileService;
import com.totallytot.services.JiraRestApiServiceCookieImpl;

import java.util.Set;

public class JiraWorkflowService implements FileService {
    private String baseUrl;
    private JiraRestApiServiceCookieImpl jiraRestApiCookie;

    public JiraWorkflowService() {
        this.baseUrl = Authenticator.getBaseURL();
        this.jiraRestApiCookie = new JiraRestApiServiceCookieImpl();
    }

    public void removeWorkflowSchemes() {
        Set<String> ids = loadDataFromFile(ToolUtils.filePath);
        ToolUtils.print("Total items for processing: " + ids.size());
        ToolUtils.print("Getting cookies...");
        String cookie = jiraRestApiCookie.getCookie();
        ToolUtils.print("Getting WebSudo rights...");
        jiraRestApiCookie.activateWebSudo(cookie);
        ToolUtils.print("Starting workflow schemes removal...");

        ids.forEach(id -> {
            ToolUtils.print("Processing workflow scheme id: " + id);
            String jiraRestApiUrl = String.format("%srest/api/latest/workflowscheme/%s", baseUrl, id);
            int status = jiraRestApiCookie.sendDeleteRequest(jiraRestApiUrl, cookie);
            switch (status) {
                case 400:
                    ToolUtils.print("Status " + status + ": requested scheme is active.");
                    break;
                case 401:
                    ToolUtils.print("Status " + status + ": there is no user or the user has not entered a websudo session.");
                    break;
                case 404:
                    ToolUtils.print("Status " + status + ": requested scheme does not exist.");
                    break;
                case 204:
                    ToolUtils.print("Status " + status + ": the scheme was deleted.");
                    break;
                default:
                    ToolUtils.print("Nothing happened. Check settings!");
                    break;
            }
        });
    }
}