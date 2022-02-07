package com.ayush.excel;

import com.ayush.excel.model.Student;
import com.ayush.excel.repo.StudentRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.PrePersist;

@SpringBootApplication
public class ExcelServiceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ExcelServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(StudentRepo studentRepo) {
		return (args -> {
			for (int i=0;i<10000;i++){
				studentRepo.save(new Student("Ayush","Gokarna","BCA"));
			}
		});
	}
}
