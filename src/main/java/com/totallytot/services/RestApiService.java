package com.totallytot.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public interface RestApiService {

    default int sendPostRequest(String restApiUrl, String basicAuth, String body) {
        int status = 0;
        try {
            URL url = new URL(restApiUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Authorization", basicAuth);
            httpCon.setRequestProperty("Content-Type", "Application/json");
            httpCon.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(body);
            out.close();
            status = httpCon.getResponseCode();
            httpCon.getInputStream();  //????

        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}