package com.totallytot.reports;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class IssueTypesReport extends BasicReport {

    public IssueTypesReport(JiraRestClient jiraRestClient) {
        super(jiraRestClient);
        reportName = "IssueTypes";
        thirdCellName = "Is Sub-Task?";
    }

    @Override
    public Workbook generateReport() {
        Workbook workbook = createWorkbook();
        Sheet sheet = workbook.getSheet(reportName);

        Iterable<IssueType> issueTypes = null;
        try {
            issueTypes = jiraRestClient.getMetadataClient().getIssueTypes().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        AtomicInteger rowIndex = new AtomicInteger(2);

        assert issueTypes != null;
        issueTypes.forEach(issueType -> {
            Row row = sheet.createRow(rowIndex.get());
            Cell cell = row.createCell(0);
            cell.setCellValue(issueType.getId());
            cell = row.createCell(1);
            cell.setCellValue(issueType.getName());
            cell = row.createCell(2);
            cell.setCellValue(issueType.isSubtask());
            rowIndex.getAndIncrement();
        });

        return workbook;
    }
}