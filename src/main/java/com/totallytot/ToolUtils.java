package com.totallytot;

import java.io.IOException;
import java.util.Properties;

public class ToolUtils {
    public static String filePath;
    static final String VERSION = "REST API Tool for Atlassian apps v.1.2.1";

    public static void print(Object text) {
        System.out.println(text);
    }

    static void loadProperties() {
        Properties prop = new Properties();
        try {
            prop.load(Tool.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            ToolUtils.print("Unable to find config.properties");
            e.printStackTrace();
        }
        filePath = prop.getProperty("input.file.location");
        Authenticator.setJiraUsername(prop.getProperty("jira.username"));
        Authenticator.setJiraPassword(prop.getProperty("jira.password"));
        Authenticator.setCrowdApplicationUser(prop.getProperty("crowd.application.user"));
        Authenticator.setCrowdApplicationPassword(prop.getProperty("crowd.application.password"));
        Authenticator.setJiraCloudUsername(prop.getProperty("jiracloud.username"));
        Authenticator.setJiraCloudPassword(prop.getProperty("jiracloud.password"));
    }

    static void showHelp() {
        ToolUtils.print("Example of usage: java -jar atlassian-rest-api-tool.jar jira http://localhost:8080 -it");
        ToolUtils.print("Available commands: ");
        ToolUtils.print("   -help - shows help (current message);");
        ToolUtils.print("   -version - shows actual version of the tool;");
        ToolUtils.print("   crowd [Base URL] -cu - creates active users based on input data. Input format: username,password,first,last,email. One user per line.");
        ToolUtils.print("   crowd [Base URL] -ug [Group] - updates group membership based on input data. Input format: username. One user per line.");
        ToolUtils.print("   jira [Base URL] -r - generates resolutions report. The output is xlsx file located in the same dir as jar file.");
        ToolUtils.print("   jira [Base URL] -it - generates issue types report. The output is xlsx file located in the same dir as jar file.");
        ToolUtils.print("   jira [Base URL] -cf - generates custom fields report. The output is xlsx file located in the  same dir as jar file.");
        ToolUtils.print("   jira [Base URL] -ws - generates workflow statuses report. The output is xlsx file located in the same dir as jar file.");
        ToolUtils.print("   jira [Base URL] -rws - removes workflow schemes based on input data. Input format: Workflow scheme ids. One id per line.");
        ToolUtils.print("   jirac [Base URL] -uv id - updates version picker field based on input data. Input format: issuekey,value1,value2. One issue key per line.");
    }
}