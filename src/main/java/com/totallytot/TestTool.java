package com.totallytot;

import com.totallytot.reports.CustomFieldsReport;
import com.totallytot.reports.IssueTypesReport;
import com.totallytot.reports.ResolutionsReport;
import com.totallytot.reports.WorkflowStatusesReport;
import com.totallytot.services.JiraRestApiServiceCookieImpl;
import com.totallytot.services.RestApiService;
import com.totallytot.services.crowd.CrowdGroupService;
import com.totallytot.services.crowd.CrowdUserService;
import com.totallytot.services.jira.JiraWorkflowService;

public class TestTool {
    public static void main(String[] args) {
        //CrowdUserService crowdUserService = new CrowdUserService(ToolUtils.encodeCredentials("crowd", "crowd"), "http://localhost:8095/crowd/");
       //crowdUserService.createUsers();
        //CrowdGroupService crowdGroupService = new CrowdGroupService(ToolUtils.encodeCredentials("crowd", "crowd"), "http://localhost:8095/crowd/");
        //crowdGroupService.updateGroupMembership("test");

        /*
        IssueTypesReport it = new IssueTypesReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        it.writeXlsxFile(it.generateReport(), "IssueTypes");
        ResolutionsReport r = new ResolutionsReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        r.writeXlsxFile(r.generateReport(), "Resolutions");

        WorkflowStatusesReport ws = new WorkflowStatusesReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        ws.writeXlsxFile(ws.generateReport(), "WorkflowStatuses");
        */

        String baseUrl = "http://localhost:8080/";
        ToolUtils.loadProperties();
        ToolUtils.setBaseURL(baseUrl);
        JiraWorkflowService jiraWorkflowService = new JiraWorkflowService();
        jiraWorkflowService.removeWorkflowSchemes();

        //JiraRestApiServiceCookieImpl jiraRestApiServiceCookie = new JiraRestApiServiceCookieImpl(baseUrl);
        //String cook = jiraRestApiServiceCookie.getCookie();
        //System.out.println(cook);
        //jiraRestApiServiceCookie.activateWebSudo(cook);

       // CustomFieldsReport cf = new CustomFieldsReport(ToolUtils.getJiraRestClient(baseUrl));
        //cf.writeXlsxFile(cf.generateReport(), "CustomFields");
    }
}
