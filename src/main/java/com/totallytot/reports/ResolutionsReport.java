package com.totallytot.reports;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Resolution;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ResolutionsReport extends BasicReport {

    public ResolutionsReport(JiraRestClient jiraRestClient) {
        super(jiraRestClient);
        reportName = "Resolutions";
    }

    @Override
    public Workbook generateReport() {
        Workbook workbook = createWorkbook();
        Sheet sheet = workbook.getSheet(reportName);

        Iterable<Resolution> resolutions = null;
        try {
            resolutions = jiraRestClient.getMetadataClient().getResolutions().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert resolutions != null;

        AtomicInteger rowIndex = new AtomicInteger(2);
        resolutions.forEach(resolution -> {
            Row row = sheet.createRow(rowIndex.get());
            Cell cell = row.createCell(0);
            cell.setCellValue(resolution.getId());
            cell = row.createCell(1);
            cell.setCellValue(resolution.getName());
            rowIndex.getAndIncrement();
        });

        return workbook;
    }
}