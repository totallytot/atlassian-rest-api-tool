package com.totallytot.reports;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Field;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomFieldsReport extends BasicReport {

    public CustomFieldsReport(JiraRestClient jiraRestClient) {
        super(jiraRestClient);
        reportName = "CustomFields";
        thirdCellName = "Type";
    }

    @Override
    public Workbook generateReport() {
        Workbook workbook = createWorkbook();
        Sheet sheet = workbook.getSheet(reportName);

        Iterable<Field> fields = null;
        try {
            fields = jiraRestClient.getMetadataClient().getFields().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert fields != null;

        AtomicInteger rowIndex = new AtomicInteger(2);
        fields.forEach(field -> {
            if (field.getFieldType().name().equals("CUSTOM")) {
                Row row = sheet.createRow(rowIndex.get());
                Cell cell = row.createCell(0);
                cell.setCellValue(field.getSchema().getCustomId());
                cell = row.createCell(1);
                cell.setCellValue(field.getName());
                cell = row.createCell(2);
                cell.setCellValue(field.getSchema().getCustom().substring(field.getSchema().getCustom().indexOf(":")+1));
                rowIndex.getAndIncrement();
            }
        });

        return workbook;
    }
}
