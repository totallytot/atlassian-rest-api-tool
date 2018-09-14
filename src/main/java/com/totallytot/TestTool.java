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
       // crowdUserService.createUsers();
        //CrowdGroupService crowdGroupService = new CrowdGroupService(ToolUtils.encodeCredentials("crowd", "crowd"), "http://localhost:8095/crowd/");
        //crowdGroupService.updateGroupMembership("test");

        //String baseUrl = "http://localhost:8080/";
        //String jiraUsername = "admin";
        //String jiraPassword = "admin";

        //JiraWorkflowService jiraWorkflowService = new JiraWorkflowService(ToolUtils.encodeCredentials("crowd", "crowd"), "http://localhost:8080/");
        //jiraWorkflowService.removeWorkflowSchemes();
        //System.out.println("дратуте");
        /*
        IssueTypesReport it = new IssueTypesReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        it.writeXlsxFile(it.generateReport(), "IssueTypes");
        ResolutionsReport r = new ResolutionsReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        r.writeXlsxFile(r.generateReport(), "Resolutions");
        CustomFieldsReport cf = new CustomFieldsReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        cf.writeXlsxFile(cf.generateReport(), "CustomFields");
        WorkflowStatusesReport ws = new WorkflowStatusesReport(ToolUtils.getJiraRestClient(baseUrl, jiraUsername, jiraPassword));
        ws.writeXlsxFile(ws.generateReport(), "WorkflowStatuses");
        */

        JiraRestApiServiceCookieImpl jiraRestApiServiceCookie = new JiraRestApiServiceCookieImpl();
        System.out.println(jiraRestApiServiceCookie.getCookie());
    }
}
