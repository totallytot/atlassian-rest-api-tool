package com.totallytot.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public interface RestApiService {

    default int sendRequestAndGetStatus(String type, String restApiUrl, String basicAuth, String body) {
        int status = 0;
        try {
            URL url = new URL(restApiUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestProperty("Authorization", basicAuth);
            httpCon.setRequestProperty("Content-Type", "Application/json");
            httpCon.setRequestMethod(type);
            if (body != null) {
                httpCon.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
                out.write(body);
                out.close();
            }
            status = httpCon.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    default String sendRequestAndGetBody(String type, String restApiUrl, String basicAuth, String body) {

        OutputStreamWriter out = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = null;

        try {
            URL url = new URL(restApiUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Authorization", basicAuth);
            httpCon.setRequestProperty("Content-Type", "Application/json");
            httpCon.setRequestMethod(type);

            if (body != null) {
                out = new OutputStreamWriter(httpCon.getOutputStream());
                out.write(body);
            }

            reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (out != null) out.close();
                if (reader != null) reader.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    default int sendPostRequest(String restApiUrl, String basicAuth, String body) {
        return sendRequestAndGetStatus("POST", restApiUrl, basicAuth, body);
    }

    default int sendDeleteRequest(String restApiUrl, String basicAuth) {
        return sendRequestAndGetStatus("DELETE", restApiUrl, basicAuth, null);
    }
}