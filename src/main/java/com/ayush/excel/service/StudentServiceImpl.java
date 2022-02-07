package com.ayush.excel.service;

import com.ayush.excel.excel.ExcelService;
import com.ayush.excel.model.Student;
import com.ayush.excel.repo.StudentRepo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepo studentRepo;
    private ExcelService excelService;

    public StudentServiceImpl(StudentRepo studentRepo, ExcelService excelService) {
        this.studentRepo = studentRepo;
        this.excelService = excelService;
    }

    @Override
    public void uploadToDB(MultipartFile file) throws IOException {
        InputStream inputStream=file.getInputStream();
        List<Student> studentList = excelService.covertToEntity(inputStream);
        try {
            studentRepo.saveAll(studentList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Workbook exportToExcel() throws IOException {
        List<Student> studentList = studentRepo.findAll();
        Workbook workbook = excelService.convertToExcelFile(studentList);
        return workbook;

    }
}
