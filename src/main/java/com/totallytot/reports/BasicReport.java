package com.totallytot.reports;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.totallytot.ToolUtils;
import com.totallytot.services.FileService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.concurrent.ExecutionException;

public abstract class BasicReport implements FileService {
    JiraRestClient jiraRestClient;
    String firstCellName, secondCellName, thirdCellName, reportName;

    BasicReport() {
        this.jiraRestClient = ToolUtils.getJiraRestClient();
        firstCellName = "ID";
        secondCellName = "Name";
        thirdCellName = null;
    }

    public abstract Workbook generateReport();

    Workbook createWorkbook() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(reportName);
        Row firstRow = sheet.createRow(0);
        Cell firstCell = firstRow.createCell(0);

        try {
            firstCell.setCellValue("Generated from " + jiraRestClient.getSessionClient().getCurrentSession().get()
                    .getUserUri().getHost());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Row header = sheet.createRow(1);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(firstCellName);
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue(secondCellName);
        headerCell.setCellStyle(headerStyle);

        if (thirdCellName != null) {
            headerCell = header.createCell(2);
            headerCell.setCellValue(thirdCellName);
            headerCell.setCellStyle(headerStyle);
        }
        return workbook;
    }
}
