package com.hvn.processexcel.service;

import com.hvn.processexcel.model.User;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingWorkbookReader;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExportExcel {

    // Use SXSSFWorkbook fast than XSSFWorkbook
    //https://levelup.gitconnected.com/how-to-write-a-million-rows-in-an-excel-file-efficiently-5f7ce6c69314
    private SXSSFWorkbook workbook;
    private SXSSFSheet sheet;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private void writeHeaderLine() {
        workbook = new SXSSFWorkbook(1);
        sheet = workbook.createSheet("Users");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Roles", style);
        createCell(row, 4, "Enabled", style);
        createCell(row, 5, "Created Date", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.trackColumnForAutoSizing(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(List<User> listUsers) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getUserId(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getFullName(), style);
            createCell(row, columnCount++, user.getRoleId(), style);
            createCell(row, columnCount++, user.isEnabled(), style);
            createCell(row, columnCount, user.getCreatedDate().toString(), style);
        }
    }

    public void export(HttpServletResponse response, List<User> listUsers) throws IOException {
        writeHeaderLine();
        writeDataLines(listUsers);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.dispose();
        workbook.close();
        outputStream.close();
    }

    public List<User> importExcel(MultipartFile files) throws IOException {
        List<User> tempUsers = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            //CommonUtils.isBlankOrEmpty(roleId) ? 3 : Integer.parseInt(roleId)
            LocalDateTime dateTime = LocalDateTime.parse(row.getCell(4).getStringCellValue());
            User user = User.builder()
                    .email(row.getCell(0).getStringCellValue())
                    .fullName(row.getCell(1).getStringCellValue())
                    .password("123456aA")
                    .roleId((int) row.getCell(2).getNumericCellValue())
                    .isEnabled(row.getCell(3).getBooleanCellValue())
                    .createdDate(dateTime)
                    .build();
            tempUsers.add(user);
        }
        return tempUsers;
    }

    public List<User> readLargeExcel(MultipartFile files) throws IOException {
        List<User> result = new ArrayList<>();
        int rowCount = 1;
        try (InputStream is = new BufferedInputStream(files.getInputStream());) {
            Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (rowCount == 1) {
                    // skip header
                    rowCount++;
                } else {
                    LocalDateTime dateTime = LocalDateTime.parse(row.getCell(4).getStringCellValue());
                    User user = User.builder()
                            .email(row.getCell(0).getStringCellValue())
                            .fullName(row.getCell(1).getStringCellValue())
                            .password("123456aA")
                            .roleId((int) row.getCell(2).getNumericCellValue())
                            .isEnabled(row.getCell(3).getBooleanCellValue())
                            .createdDate(dateTime)
                            .build();

                    result.add(user);
                }
            }
        } catch (IOException e) {
            throw new IOException("fail to read excel file : " + e.getMessage(), e);
        }
        return result;
    }

}
