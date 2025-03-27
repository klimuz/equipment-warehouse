package com.klimuz.hardwarewarehouse;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelUtils {
    public static File createExcelFile(Context context, ArrayList<Equipment> items, int jobIndex) {//ArrayList<Equipment> items
        String column0 = context.getString(R.string.excel_rowname_tool);
        String column1 = context.getString(R.string.excel_row_name_quantity);

        Workbook workbook = new XSSFWorkbook();
        String jobName = Globals.jobs.get(jobIndex);
//        Log.i("xls", Globals.jobs.get(0));
        Sheet sheet = workbook.createSheet(jobName);

        // Создаем стиль для заголовка (жирные границы)
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop(BorderStyle.THICK);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setBorderLeft(BorderStyle.THICK);
        headerStyle.setBorderRight(BorderStyle.THICK);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Создаем стиль для обычных ячеек (тонкие границы)
        CellStyle cellStyleLeft = workbook.createCellStyle();
        cellStyleLeft.setBorderTop(BorderStyle.THIN);
        cellStyleLeft.setBorderBottom(BorderStyle.THIN);
        cellStyleLeft.setBorderLeft(BorderStyle.THICK);
        cellStyleLeft.setBorderRight(BorderStyle.THIN);

        CellStyle cellStyleRight = workbook.createCellStyle();
        cellStyleRight.setBorderTop(BorderStyle.THIN);
        cellStyleRight.setBorderBottom(BorderStyle.THIN);
        cellStyleRight.setBorderLeft(BorderStyle.THIN);
        cellStyleRight.setBorderRight(BorderStyle.THICK);

        CellStyle cellStyleBottomLeft = workbook.createCellStyle();
        cellStyleBottomLeft.setBorderTop(BorderStyle.THIN);
        cellStyleBottomLeft.setBorderBottom(BorderStyle.THICK);
        cellStyleBottomLeft.setBorderLeft(BorderStyle.THICK);
        cellStyleBottomLeft.setBorderRight(BorderStyle.THIN);

        CellStyle cellStyleBottomRight = workbook.createCellStyle();
        cellStyleBottomRight.setBorderTop(BorderStyle.THIN);
        cellStyleBottomRight.setBorderBottom(BorderStyle.THICK);
        cellStyleBottomRight.setBorderLeft(BorderStyle.THIN);
        cellStyleBottomRight.setBorderRight(BorderStyle.THICK);


        // Заголовки
        Row rowName = sheet.createRow(0);
        Cell cellName = rowName.createCell(0);
        cellName.setCellValue(jobName);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        cellName.setCellStyle(headerStyle);
        rowName.createCell(1).setCellStyle(headerStyle);

        Row headerRow = sheet.createRow(1);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue(column0);
        headerCell.setCellStyle(headerStyle);
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue(column1);
        headerCell.setCellStyle(headerStyle);

        // Данные
        int total = 0;
        for (Equipment equipment : items) {
            if (equipment.getJobsInfo(jobIndex) > 0) {
                total++;
            }
        }

        ArrayList<String> itemNames = new ArrayList<>();
        ArrayList<Integer> itemQuantity = new ArrayList<>();
        for (Equipment equipment : items){
            int selectedJobValue = equipment.getJobsInfo(jobIndex);
            if (selectedJobValue > 0) {
                itemNames.add(equipment.getName());
                itemQuantity.add(selectedJobValue);
            }
        }

        int rowCounter = 2;
        for (String name : itemNames){
            Row dataRow = sheet.createRow(rowCounter);
            Cell dataCellLeft = dataRow.createCell(0);
            Cell dataCellRight = dataRow.createCell(1);
            if (rowCounter == itemNames.size() + 1){
                dataCellLeft.setCellStyle(cellStyleBottomLeft);
                dataCellRight.setCellStyle(cellStyleBottomRight);
            } else {
                dataCellLeft.setCellStyle(cellStyleLeft);
                dataCellRight.setCellStyle(cellStyleRight);
            }
            dataCellLeft.setCellValue(name);
            dataCellRight.setCellValue(itemQuantity.get(itemNames.indexOf(name)));
            rowCounter ++;
        }

//        for (int i = 0; i < itemNames.size() ; i++) {
//            Row dataRow = sheet.createRow(i + 2);
//            Cell dataCellLeft = dataRow.createCell(0);
//            Cell dataCellRight = dataRow.createCell(1);
//            if (i == itemNames.size() - 1){
//                dataCellLeft.setCellStyle(cellStyleBottomLeft);
//                dataCellRight.setCellStyle(cellStyleBottomRight);
//            } else {
//                dataCellLeft.setCellStyle(cellStyleLeft);
//                dataCellRight.setCellStyle(cellStyleRight);
//            }
//            dataCellLeft.setCellValue(itemNames.get(i));
//            dataCellRight.setCellValue(itemQuantity.get(i));
//        }


//        sheet.autoSizeColumn(0);
//        sheet.autoSizeColumn(1);

        // Сохранение файла
        String fileName = String.format("%s.xls", jobName);

        File downloadDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        if (!downloadDir.exists()) {
            downloadDir.mkdirs(); // Создаём папку, если её нет
        }

        File file = new File(downloadDir, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            workbook.close();


            MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    // Логика после завершения сканирования
                }
            });

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

