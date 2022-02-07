package com.ayush.excel.excel;

import com.ayush.excel.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelServiceImpl implements ExcelService {

    @Override
    public Workbook convertToExcelFile(List<Student> studentList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        int flag=1000;
        int rowIndex=1;
        for (int i = 0; i < studentList.size(); i++) {
            if (flag==i){
                flag+=1000;
                sheet = workbook.createSheet("sheet"+i);
                rowIndex=1;
            }
            sheet.setColumnWidth(0, 2000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 3000);

            Row header = sheet.createRow(0);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Id");

            headerCell = header.createCell(1);
            headerCell.setCellValue("Name");

            headerCell = header.createCell(2);
            headerCell.setCellValue("Address");

            headerCell = header.createCell(3);
            headerCell.setCellValue("Faculty");

            Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(studentList.get(i).getId()));
            cell = row.createCell(1);
            cell.setCellValue(studentList.get(i).getName());
            cell=row.createCell(2);
            cell.setCellValue(studentList.get(i).getAddress());
            cell=row.createCell(3);
            cell.setCellValue(studentList.get(i).getFaculty());
            rowIndex++;
        }
        return workbook;
    }


    @Override
    public List<Student> covertToEntity(InputStream stream) {
        List<Student> studentList = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(stream);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = (Row) rows.next();

                /*Skips Heading*/
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator cellsInRow = currentRow.iterator();
                Student student = new Student();
                int columnIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCellInRow = (Cell) cellsInRow.next();
                    switch (columnIndex) {
                        case 0:
                            student.setName(currentCellInRow.getStringCellValue());
                            break;
                        case 1:
                            student.setAddress(currentCellInRow.getStringCellValue());
                            break;
                        case 2:
                            student.setFaculty(currentCellInRow.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    columnIndex++;
                }
                studentList.add(student);
            }
            workbook.close();
            return studentList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
