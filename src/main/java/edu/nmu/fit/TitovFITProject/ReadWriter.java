package edu.nmu.fit.TitovFITProject;

import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriter {
    private static final String SHEET_NAME = "Items";
    private static int ROW_NUM = 0;
    public byte[] saveToFile(XSSFWorkbook workbook){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try (bos) {
                workbook.write(bos);
            }
            byte[] bytes = bos.toByteArray();
            workbook.close();
            System.out.println("File saved successfully");
            return bytes;
        }catch (IOException e){
            e.printStackTrace();
        }


        return null;
    }

    public void pushDataToFile(List<String> data, XSSFWorkbook workbook){
        if (data==null) return;
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        XSSFRow row = sheet.createRow(ROW_NUM);

        for (int i=0; i< data.size();i++){
            row.createCell(i).setCellValue(data.get(i));
        }
        ROW_NUM++;


    }

    public XSSFWorkbook startFile(){
        ROW_NUM = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        workbook.createSheet(SHEET_NAME);
        List<String> data = new ArrayList<>();
        data.add("Number");
        data.add("Description");

        data.add("Link");
        data.add("Price");
        pushDataToFile(data, workbook);
        return workbook;
    }
}
