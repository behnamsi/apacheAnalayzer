package com.behnam.school.management.course;

import com.behnam.school.management.professor.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/course/")
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping(path = "all/")
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @PostMapping(path = "add/")
    public void addCourse(@RequestBody Course course,
                          @RequestParam() Long professorId,
                          @RequestParam() Long collegeId
                          ) {
        service.addCourse(course,professorId,collegeId);
    }

    @DeleteMapping("delete/name/{courseName}")
    public void deleteCourseByName(
            @PathVariable("courseName") String courseName
    ) {
        service.deleteCourseByName(courseName);
    }

    @DeleteMapping(path = "delete/id/{courseId}")
    public void deleteCourseById(
            @PathVariable("courseId") Long courseId
    ) {
        service.deleteCourseById(courseId);
    }

    @PutMapping(path = "update/{courseId}")
    public void updateCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) Integer unitNumber,
            @RequestParam(required = false) Long professorId
    ) {
        service.updateCourse(courseId, courseName, unitNumber, professorId);
    }
}
