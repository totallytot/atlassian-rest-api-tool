package com.totallytot;

import com.totallytot.reports.CustomFieldsReport;
import com.totallytot.reports.IssueTypesReport;
import com.totallytot.reports.ResolutionsReport;
import com.totallytot.reports.WorkflowStatusesReport;
import com.totallytot.services.crowd.CrowdGroupService;
import com.totallytot.services.crowd.CrowdUserService;
import com.totallytot.services.jira.JiraWorkflowService;

public class Tool {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-version")) ToolUtils.print(ToolUtils.VERSION);
        else if (args.length == 1 && args[0].equals("-help")) ToolUtils.showHelp();
        else {
            ToolUtils.loadProperties();
            String application = args[0].toLowerCase().trim();
            //set base url in util tools as all classes will use it from tool utils
            String baseUrl = args[1].toLowerCase().trim();
            if (!baseUrl.endsWith("/")) baseUrl = baseUrl + "/";
            ToolUtils.setBaseURL(baseUrl);

            String key = args[2].toLowerCase().trim();

            switch (application) {
                case "crowd":
                    switch (key) {
                        case "-ug":
                            new CrowdGroupService().updateGroupMembership(args[3].trim());
                            break;
                        case "-cu":
                            new CrowdUserService().createUsers();
                            break;
                        default:
                            ToolUtils.showHelp();
                            break;
                    }
                    break;
                case "jira":
                    switch (key) {
                        case "-r":
                            ResolutionsReport r = new ResolutionsReport();
                            r.writeXlsxFile(r.generateReport(), "Resolutions");
                            break;
                        case "-it":
                            IssueTypesReport it = new IssueTypesReport();
                            it.writeXlsxFile(it.generateReport(), "IssueTypes");
                            break;
                        case "-cf":
                            CustomFieldsReport cf = new CustomFieldsReport();
                            cf.writeXlsxFile(cf.generateReport(), "CustomFields");
                            break;
                        case "-ws":
                            WorkflowStatusesReport ws = new WorkflowStatusesReport();
                            ws.writeXlsxFile(ws.generateReport(), "WorkflowStatuses");
                            break;
                        case "-rw":
                            JiraWorkflowService jiraWorkflowService = new JiraWorkflowService();
                            jiraWorkflowService.removeWorkflowSchemes();
                            break;
                        default:
                            ToolUtils.showHelp();
                            break;
                    }
                    break;
                default:
                    ToolUtils.showHelp();
                    break;
            }
        }
    }
}