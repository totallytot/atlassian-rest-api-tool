package com.totallytot.services.crowd;

import com.totallytot.ToolUtils;
import com.totallytot.services.FileService;
import com.totallytot.services.RestApiService;

import java.util.Set;

public class CrowdUserService implements RestApiService, FileService {

    private String basicAuth, baseUrl;

    public CrowdUserService(String baseUrl) {
        this.basicAuth = ToolUtils.getCrowdBasicAuth();
        this.baseUrl = baseUrl;
    }

    public void createUsers() {
        Set<String> usersData = loadDataFromFile(ToolUtils.filePath);
        ToolUtils.print("Total items for processing: " + usersData.size());
        ToolUtils.print("Starting creation of users...");

        usersData.forEach((userData) -> {
            String crowdRestApiUrl = String.format("%srest/usermanagement/latest/user", baseUrl);

            String[] userDataArr = userData.split(",");
            String name = userDataArr[0];
            ToolUtils.print("Processing user: " + name);
            String password = userDataArr[1].trim();
            String firstName = userDataArr[2].trim();
            String lastName = userDataArr[3].trim();
            String email = userDataArr[4].trim();
            String displayName = firstName + " " + lastName;

            String body = String.format("{\"name\": \"%s\",\"password\": {\"value\": \"%s\"},\"active\": true," +
                    "\"first-name\": \"%s\",\"last-name\": \"%s\",\"display-name\": \"%s\",\"email\": \"%s\"}",
                    name, password, firstName, lastName, displayName, email);

            int status = sendPostRequest(crowdRestApiUrl, basicAuth, body);

            String response;
            switch (status) {
                case 400:
                    response = "Status " + status + ": invalid user data, for example missing password or the user " +
                            "already exists.";
                    ToolUtils.print(response);
                    break;
                case 403:
                    response = "Status " + status + ": the application is not allowed to create a new user.";
                    ToolUtils.print(response);
                    break;
                case 201:
                    response = "Status " + status + ": the user was successfully created.";
                    ToolUtils.print(response);
                    break;
                default:
                    ToolUtils.print("Nothing happened. Check settings!");
                    break;
            }
        });
        ToolUtils.print("Done.");
    }
}