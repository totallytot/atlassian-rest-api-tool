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
    private static String jiraUsername, jiraPassword, crowdApplicationUser, crowdApplicationPassword;
    private static final String VERSION = "REST API Tool for Atlassian apps v.1.1";
    public static final String FILENAME = "./input.csv";
    //public static final String FILENAME = "d:/input.csv";


    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-version")) ToolUtils.print(VERSION);
        else if (args.length == 1 && args[0].equals("-help")) ToolUtils.showHelp();
        else {
            loadProperties();
            String application = args[0].toLowerCase().trim();
            String baseUrl = args[1].toLowerCase().trim();
            String key = args[2].toLowerCase().trim();

            if (!baseUrl.endsWith("/")) baseUrl = baseUrl + "/";

            switch (application)
            {
                case "crowd":
                    String basicAuth = ToolUtils.encodeCredentials(crowdApplicationUser, crowdApplicationPassword);
                    switch (key) {
                        case "-ug":
                            new CrowdGroupService(basicAuth, baseUrl).updateGroupMembership(args[3].trim());
                            break;
                        case "-cu":
                            new CrowdUserService(basicAuth, baseUrl).createUsers();
                            break;
                        default:
                            ToolUtils.showHelp();
                            break;
                    }
                    break;

                case "jira":
                    switch (key) {
                        case "-r":
                            ResolutionsReport r = new ResolutionsReport(ToolUtils.getJiraRestClient(baseUrl,
                                    jiraUsername, jiraPassword));
                            r.writeXlsxFile(r.generateReport(), "Resolutions");
                            break;
                        case "-it":
                            IssueTypesReport it = new IssueTypesReport(ToolUtils.getJiraRestClient(baseUrl,
                                    jiraUsername, jiraPassword));
                            it.writeXlsxFile(it.generateReport(), "IssueTypes");
                            break;
                        case "-cf":
                            CustomFieldsReport cf = new CustomFieldsReport(ToolUtils.getJiraRestClient(baseUrl,
                                    jiraUsername, jiraPassword));
                            cf.writeXlsxFile(cf.generateReport(), "CustomFields");
                            break;
                        case "-ws":
                            WorkflowStatusesReport ws = new WorkflowStatusesReport(ToolUtils.getJiraRestClient(baseUrl,
                                    jiraUsername, jiraPassword));
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

    private static void loadProperties() {
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
    }
}
