package com.ayush.excel.excel;

import com.ayush.excel.model.Student;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    Workbook convertToExcelFile(List<Student> studentList) throws IOException;

    List<Student> covertToEntity(InputStream stream);
}
