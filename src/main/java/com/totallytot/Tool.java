package com.totallytot;

import com.totallytot.reports.CustomFieldsReport;
import com.totallytot.reports.IssueTypesReport;
import com.totallytot.reports.ResolutionsReport;
import com.totallytot.reports.WorkflowStatusesReport;
import com.totallytot.services.crowd.CrowdGroupService;
import com.totallytot.services.crowd.CrowdUserService;

import java.io.IOException;
import java.util.Properties;

public class Tool {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-version")) ToolUtils.print(ToolUtils.VERSION);
        else if (args.length == 1 && args[0].equals("-help")) ToolUtils.showHelp();
        else {
            ToolUtils.loadProperties();
            String application = args[0].toLowerCase().trim();
            String baseUrl = args[1].toLowerCase().trim();
            String key = args[2].toLowerCase().trim();
            if (!baseUrl.endsWith("/")) baseUrl = baseUrl + "/";

            switch (application) {
                case "crowd":
                    switch (key) {
                        case "-ug":
                            new CrowdGroupService(baseUrl).updateGroupMembership(args[3].trim());
                            break;
                        case "-cu":
                            new CrowdUserService(baseUrl).createUsers();
                            break;
                        default:
                            ToolUtils.showHelp();
                            break;
                    }
                    break;
                case "jira":
                    switch (key) {
                        case "-r":
                            ResolutionsReport r = new ResolutionsReport(ToolUtils.getJiraRestClient(baseUrl));
                            r.writeXlsxFile(r.generateReport(), "Resolutions");
                            break;
                        case "-it":
                            IssueTypesReport it = new IssueTypesReport(ToolUtils.getJiraRestClient(baseUrl));
                            it.writeXlsxFile(it.generateReport(), "IssueTypes");
                            break;
                        case "-cf":
                            CustomFieldsReport cf = new CustomFieldsReport(ToolUtils.getJiraRestClient(baseUrl));
                            cf.writeXlsxFile(cf.generateReport(), "CustomFields");
                            break;
                        case "-ws":
                            WorkflowStatusesReport ws = new WorkflowStatusesReport(ToolUtils.getJiraRestClient(baseUrl));
                            ws.writeXlsxFile(ws.generateReport(), "WorkflowStatuses");
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