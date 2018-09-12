package com.totallytot;

import com.totallytot.services.crowd.CrowdGroupService;
import com.totallytot.services.crowd.CrowdUserService;

import java.io.IOException;
import java.util.Properties;

public class Tool {
    private static String jiraUsername, jiraPassword, crowdApplicationUser, crowdApplicationPassword;
    private static final String VERSION = "REST API Tool for Atlassian apps v.1.0";
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

            if (!baseUrl.endsWith("/")) {
                baseUrl = baseUrl + "/";
            }

            switch (application) {
                case "crowd":
                    String basicAuth = ToolUtils.encodeCredentials(crowdApplicationUser, crowdApplicationPassword);
                if (key.equals("-ug")) new CrowdGroupService(basicAuth, baseUrl).updateGroupMembership(args[3]);
                else if (key.equals("-cu")) new CrowdUserService(basicAuth, baseUrl).createUsers();
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
