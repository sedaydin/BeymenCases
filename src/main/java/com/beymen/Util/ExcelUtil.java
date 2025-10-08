package com.beymen.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {
    private static final Logger logger = LogManager.getLogger(ExcelUtil.class);

    public static String getCellData(String filePath, int rowNum, int colNum) {
        String cellValue = "";
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNum);

            if (row != null) {
                Cell cell = row.getCell(colNum);
                if (cell != null) {
                    cellValue = cell.getStringCellValue();
                    logger.info("Read from Excel [Row:" + rowNum + ", Col:" + colNum + "]: " + cellValue);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading Excel file", e);
        }
        return cellValue;
    }
}