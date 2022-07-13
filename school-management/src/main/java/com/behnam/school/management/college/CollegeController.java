package com.behnam.school.management.college;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/college/")
public class CollegeController {
    private final CollegeService service;


    @Autowired
    public CollegeController(CollegeService service) {
        this.service = service;
    }

    @GetMapping(path = "all/")
    public List<College> getAllColleges() {
        return service.getAllColleges();
    }

    @PostMapping("add/")
    public void addCollege(@RequestBody College college) {
        service.addCollege(college);
    }

    @DeleteMapping(path = "delete/id/{collegeId}")
    public void deleteCollegeByID(@PathVariable("collegeId") Long collegeId) {
        service.deleteCollegeByID(collegeId);
    }

    @DeleteMapping(path = "delete/name/{collegeName}")
    public void deleteCollegeByName(@PathVariable("collegeName") String collegeName) {
        service.deleteCollegeByName(collegeName);
    }

    @PutMapping(path = "update/{collegeId}")
    public void updateCollege(
            @PathVariable("collegeId") Long collegeId,
            @RequestParam(required = false) String collegeName
    ) {
        service.updateCollege(collegeId, collegeName);
    }
}
