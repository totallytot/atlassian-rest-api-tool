package com.totallytot.services.jira;

import com.totallytot.Tool;
import com.totallytot.ToolUtils;
import com.totallytot.services.FileService;
import com.totallytot.services.RestApiService;

import java.util.Set;

public class JiraWorkflowService implements RestApiService, FileService {
    private String basicAuth, baseUrl;

    public JiraWorkflowService(String basicAuth, String baseUrl) {
        this.basicAuth = basicAuth;
        this.baseUrl = baseUrl;
    }

    public void removeWorkflowSchemes() {
        Set<String> ids = loadDataFromFile(Tool.FILENAME);
        ToolUtils.print("Total items for processing: " + ids.size());
        ToolUtils.print("Starting workflow schemes removal...");

        ids.forEach(id -> {
            ToolUtils.print("Processing workflow scheme id: " + id);
            String jiraRestApiUrl = String.format("%srest/api/latest/workflowscheme/%s", baseUrl, id);
            int status = sendDeleteRequest(jiraRestApiUrl, basicAuth);
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
