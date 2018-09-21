package com.totallytot.reports;

import com.atlassian.jira.rest.client.api.domain.Status;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkflowStatusesReport extends BasicReport {

    public WorkflowStatusesReport() {
        super();
        reportName = "WorkflowStatuses";
    }

    @Override
    public Workbook generateReport() {
        Workbook workbook = createWorkbook();
        Sheet sheet = workbook.getSheet(reportName);

        Iterable<Status> statuses = null;
        try {
            statuses = jiraRestClient.getMetadataClient().getStatuses().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert statuses != null;

        AtomicInteger rowIndex = new AtomicInteger(2);
        statuses.forEach(status -> {
            Row row = sheet.createRow(rowIndex.get());
            Cell cell = row.createCell(0);
            cell.setCellValue(status.getId());
            cell = row.createCell(1);
            cell.setCellValue(status.getName());
            rowIndex.getAndIncrement();
        });

        return workbook;
    }
}