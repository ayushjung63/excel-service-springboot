package com.ayush.excel.controller;

import com.ayush.excel.service.StudentService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.jni.File;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/upload")
    public ResponseEntity uploadExcelFile(@RequestParam("file")MultipartFile file){
        System.out.println(file.getContentType());
        if (file.getContentType()
                .equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            try {
                studentService.uploadToDB(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.OK)
                        .body("Excel file successfully upload to DB");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please upload excel file.");
        }
    }


    @GetMapping("/export-excel")
    public void export(HttpServletResponse response) {
        try {
            Workbook workbook = studentService.exportToExcel();
            String fileName="sheet.xlsx";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            ServletOutputStream outputStream=response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
