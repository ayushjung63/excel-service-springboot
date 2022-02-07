package com.ayush.excel.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface StudentService {

    public void uploadToDB(MultipartFile file) throws IOException;
    public Workbook exportToExcel() throws IOException;
}
