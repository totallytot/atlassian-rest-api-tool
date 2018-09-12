package com.totallytot.services;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public interface FileService {

    default Set<String> loadDataFromFile(String filename) {
        Set<String> data = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis)))
        {
            String idLine;
            while ((idLine = reader.readLine()) != null) {
                data.add(idLine.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
