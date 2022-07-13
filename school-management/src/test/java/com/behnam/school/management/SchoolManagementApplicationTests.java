package com.behnam.school.management;

import com.behnam.school.management.college.College;
import com.behnam.school.management.student.Student;
import com.behnam.school.management.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchoolManagementApplicationTests {

    @Autowired
    private StudentRepository repository;

    @Test
    void getStudentCollege() {

    }
//
//    @Test
//    public void deleteStudent() {
//        repository.deleteById(1L);
//    }
}
