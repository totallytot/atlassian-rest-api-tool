package com.totallytot.services.jiracloud;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.totallytot.ToolUtils;
import com.totallytot.services.FileService;
import com.totallytot.services.RestApiService;
import com.totallytot.services.RestApiServiceUnirestImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JiraIssueService implements RestApiService, FileService {
    private String baseUrl;
    private RestApiServiceUnirestImpl restApiServiceUnirest;

    public JiraIssueService() {
        this.baseUrl = ToolUtils.getBaseURL();
        this.restApiServiceUnirest = new RestApiServiceUnirestImpl();
    }

    public void updateVersionPickerCF(String id) {
        Set<String> data = loadDataFromFile(ToolUtils.filePath);
        ToolUtils.print("Total items for processing: " + data.size());
        ToolUtils.print("Starting version field update...");
        data.forEach(record -> {
            //form data
            String[] arrRecord = record.split(",");
            String restApiUrl = baseUrl + "rest/api/latest/issue/" + arrRecord[0];
            ToolUtils.print("Processing issue key: " + arrRecord[0]);
            List<String> values = new ArrayList<>();
            for (int i = 1; i < arrRecord.length; i++) values.add(arrRecord[i]);

            //create JSON boby
            JsonNodeFactory jnf = JsonNodeFactory.instance;
            ObjectNode root = jnf.objectNode();
            ObjectNode fields = root.putObject("fields");
            {
                ArrayNode customfield = fields.putArray("customfield_" + id);
                values.forEach(value -> customfield.addObject().put("name", value));
            }
            int status = restApiServiceUnirest.sendPutRequestAndGetStatus(restApiUrl,
                    ToolUtils.getJiraCloudBasicAuth(), root);
            switch (status) {
                case 204:
                    ToolUtils.print("Status " + status + ": the issue was updated successfully.");
                    break;
                case 400:
                    ToolUtils.print("Status " + status + ": the issue update has failed.");
                    break;
                case 403:
                    ToolUtils.print("Status " + status + ": override screen security but doesn't have permission to do that.");
                    break;
                default:
                    ToolUtils.print("Nothing happened. Check settings!");
                    break;
            }
        });
    }
}
