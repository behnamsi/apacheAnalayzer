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

    // get the all of students
    @GetMapping("all/")
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    //get student courses using university id
    @GetMapping(path = "courses/{uniID}")
    public List<String> getStudentCourses(
            @PathVariable("uniID") Long uniID
    ) {
        return service.getStudentCourses(uniID);
    }

    // get student averages
    @GetMapping(path = "get-avg/{uniID}")
    public Double getStudentAverage(@PathVariable("uniID") Long uniID) {
        return service.getStudentAverage(uniID);
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

    // update with university id
    @PutMapping(path = "update/{uniId}")
    public void updateStudent(
            @PathVariable("uniId") Long uniId,
            @RequestParam(required = false) String first_name,
            @RequestParam(required = false) String last_name,
            @RequestParam(required = false) List<String> courses,
            @RequestParam(required = false) Long nationalId,
            @RequestParam(required = false) Long universityId
    ) {
        service.updateStudent(uniId, first_name, last_name, courses, nationalId, universityId);
    }

    // add course for student
    @PutMapping(path = "add/score/{uniId}/{courseName}")
    public void addScoreCourse(
            @PathVariable("uniId") Long uniId,
            @PathVariable("courseName") String courseName,
            @RequestParam Double score
    ) {
        service.addScoreCourse(uniId, courseName, score);
    }

    // delete course for student
    @PutMapping(path = "{uniId}/course/delete")
    public void deleteStudentCourse(
            @PathVariable("uniId") Long uniId,
            @RequestParam String courseName
    ) {
        service.deleteStudentCourse(uniId, courseName);
    }
}
