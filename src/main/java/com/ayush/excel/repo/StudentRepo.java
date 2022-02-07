package com.ayush.excel.repo;

import com.ayush.excel.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo  extends JpaRepository<Student,Long> {

}
