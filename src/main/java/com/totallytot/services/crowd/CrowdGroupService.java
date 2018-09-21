package com.totallytot.services.crowd;

import com.totallytot.ToolUtils;
import com.totallytot.services.FileService;
import com.totallytot.services.RestApiService;

import java.util.Set;

public class CrowdGroupService implements RestApiService, FileService {

    private String basicAuth, baseUrl;

    public CrowdGroupService() {
        this.basicAuth = ToolUtils.getCrowdBasicAuth();
        this.baseUrl = ToolUtils.getBaseURL();
    }

    public void updateGroupMembership(String group) {
        Set<String> usernames = loadDataFromFile(ToolUtils.filePath);
        ToolUtils.print("Total items for processing: " + usernames.size());

        usernames.forEach((username) -> {
            ToolUtils.print("Processing user: " + username);
            String crowdRestApiUrl = String.format("%srest/usermanagement/latest/group/user/direct?groupname=%s",
                    baseUrl, group);
            String body = String.format("{\"name\":\"%s\"}", username);
            int status = sendPostRequest(crowdRestApiUrl, basicAuth, body);

            String error;
            switch (status) {
                case 400:
                    error = "Status " + status + ": the user could not be found.";
                    ToolUtils.print(error);
                    break;
                case 404:
                    error = "Status " + status + ": the group could not be found.";
                    ToolUtils.print(error);
                    break;
                case 409:
                    error = "Status " + status + ": the user is already a direct member of the group.";
                    ToolUtils.print(error);
                    break;
                case 201:
                    ToolUtils.print("Status " + status + ": the user is successfully added as a member of the group.");
                    break;
                default:
                    ToolUtils.print("Nothing happened. Check settings!");
                    break;
            }
        });
        ToolUtils.print("Done.");
    }
}
