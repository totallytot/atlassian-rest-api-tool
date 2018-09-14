package com.totallytot.services;

import org.apache.poi.ss.usermodel.Workbook;

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

    default void writeXlsxFile(Workbook workbook, String fileName) {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + fileName + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(fileLocation))
        {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
