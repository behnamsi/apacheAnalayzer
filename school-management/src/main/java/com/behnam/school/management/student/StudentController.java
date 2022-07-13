package com.behnam.school.management.student;

import com.behnam.school.management.college.College;
import com.behnam.school.management.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "api/student/")
public class StudentController {
    private final StudentService service;

    //constructor
    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    // GET methods
    @GetMapping("all/")
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }


    @GetMapping(path = "courses/{studentId}")
    public List<String> getStudentCourses(
            @PathVariable("studentId") Long studentId
    ) {
        return service.getStudentCourses(studentId);
    }

    @GetMapping(path = "get-avg/{studentId}")
    public Double getStudentAverage(@PathVariable("studentId") Long studentId) {
        return service.getStudentAverage(studentId);
    }


    //POST methods
    @PostMapping(path = "add/")
    public void addStudent(
            @RequestBody Student student,
            @RequestParam Long collegeId
    ) {
        service.addStudent(student, collegeId);
    }


    @DeleteMapping(path = "delete/{id}")
    public void deleteStudent(
            @PathVariable("id") Long id
    ) {
        service.deleteStudent(id);
    }

    @DeleteMapping(path = "delete/uni-id/{uniId}")
    public void deleteStudentByUniId(
            @PathVariable("uniId") Long uniId
    ) {

        service.deleteStudentByUniId(uniId);
    }

    @PutMapping(path = "update/{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long id,
            @RequestParam(required = false) String first_name,
            @RequestParam(required = false) String last_name,
            @RequestParam(required = false) List<String> courses,
            @RequestParam(required = false) Long nationalId,
            @RequestParam(required = false) Long universityId
    ) {
        service.updateStudent(id, first_name, last_name, courses, nationalId, universityId);
    }

    @PutMapping(path = "add/score/{studentId}/{courseName}")
    public void addScoreCourse(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseName") String courseName,
            @RequestParam Double score
    ) {
        service.addScoreCourse(studentId, courseName, score);
    }

    @PutMapping(path = "{studentId}/course/delete")
    public void deleteStudentCourse(
            @PathVariable("studentId") Long studentId,
            @RequestParam String courseName
    ) {
        service.deleteStudentCourse(studentId, courseName);
    }
}
