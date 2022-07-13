package com.behnam.school.management.student;

import com.behnam.school.management.college.College;
import com.behnam.school.management.college.CollegeRepository;
import com.behnam.school.management.course.Course;
import com.behnam.school.management.course.CourseRepository;
import com.behnam.school.management.professor.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final CollegeRepository collegeRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository repository, CollegeRepository collegeRepository, CourseRepository courseRepository) {
        this.repository = repository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void addStudent(Student student, Long collegeId) {
        if (collegeId != null) {
            Optional<Student> studentUniId = repository.findStudentByUniversityIdOptional(student.getUniversityId());
            Optional<Student> studentnatId = repository.findStudentByNationalId(student.getNationalId());
            if (studentUniId.isPresent()) {
                throw new IllegalStateException("university id is taken");
            }
            if (studentnatId.isPresent()) {
                throw new IllegalStateException("national id is taken");
            }
            College college = collegeRepository.findById(collegeId).orElseThrow(() ->
                    new IllegalStateException("invalid college id"));
            student.setStudentCollege(college);
            repository.save(student);
        }
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("student with this Id does not exists.");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteStudentByUniId(Long uniId) {
        if (!repository.existsByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id.");
        }
        repository.deleteStudentByUniversityId(uniId);
    }

    @Transactional
    public void updateStudent(Long id, String first_name, String last_name, List<String> courses,
                              Long nationalId, Long universityId) {
        Student student = repository.findById(id).orElseThrow(() -> new IllegalStateException
                ("this id does not exist to update."));

        Optional<Student> studentByNationalId = repository.findStudentByNationalId(nationalId);
        if (studentByNationalId.isPresent()) {
            throw new IllegalStateException("national id has a owner");
        }
        Optional<Student> studentByUniversityId = repository.findStudentByUniversityIdOptional(universityId);
        if (studentByUniversityId.isPresent()) {
            throw new IllegalStateException("university id has a owner");
        }

        if (first_name != null && first_name.length() > 0 &&
                !Objects.equals(student.getFirstName(), first_name)) {
            student.setFirstName(first_name);
        }
        if (last_name != null && last_name.length() > 0 &&
                !Objects.equals(student.getLastName(), last_name)) {
            student.setLastName(last_name);
        }
        if (!courses.isEmpty()) {
            List<Course> courses1 = new ArrayList<>(student.getStudentCourses());
            List<Professor> professorsOfStudent = new ArrayList<>();
            for (String courseName : courses) {
                Course course = courseRepository.findCourseByCourseName(courseName);
                if (!courseRepository.existsCourseByCourseName(courseName)) {
                    throw new IllegalStateException("invalid course name");
                }
                professorsOfStudent.add(course.getProfessor());
                courses1.add(course);
                student.setStudentCourses(courses1);
                student.setProfessorsOfStudent(professorsOfStudent);
            }
        }
        if (nationalId != null &&
                !Objects.equals(student.getLastName(), last_name)) {
            student.setNationalId(nationalId);
        }
        if (universityId != null &&
                !Objects.equals(student.getUniversityId(), universityId)) {
            student.setUniversityId(universityId);
        }
    }

    public List<String> getStudentCourses(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("invalid id"));
        List<String> courses = new ArrayList<>();
        for (Course course : student.getStudentCourses()) {
            courses.add(course.getCourseName());
        }
        return courses;
    }

    @Transactional
    public void addScoreCourse(Long studentId, String courseName, Double score) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("invalid student id")
        );
        if (!courseRepository.existsCourseByCourseName(courseName)) {
            throw new IllegalStateException("invalid course name");
        }
        List<Course> courseList = student.getStudentCourses();
        boolean courseFlag = false;
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                courseFlag = true;
                break;
            }
        }
        if (!courseFlag) {
            throw new IllegalStateException("course not defined for this student");
        }

        Map<String, Double> scoreCourse = student.getScores();
        scoreCourse.put(courseName, score);
        student.setScores(scoreCourse);
    }

    // delete a course of a student
    @Transactional
    public void deleteStudentCourse(Long studentId, String courseName) {


        Student student = repository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("invalid student id"));
        if (!courseRepository.existsCourseByCourseName(courseName)) {
            throw new IllegalStateException("invalid course name");
        }


        boolean courseFlag = false;
        List<Course> courseList = student.getStudentCourses();
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseName().equals(courseName)) {
                courseFlag = true;
                courseList.remove(i);
                break;
            }
        }
        if (!courseFlag) {
            throw new IllegalStateException("this student does not have this course");
        }
        student.setStudentCourses(courseList);
        Map<String, Double> studentScoresMap = student.getScores();
        for (String courseName1 : studentScoresMap.keySet()) {
            if (courseName1.equals(courseName)) {
                studentScoresMap.remove(courseName);
                break;
            }
        }
        student.setScores(studentScoresMap);
    }

    public Double getStudentAverage(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("invalid student id")
        );

        List<Course> courses = student.getStudentCourses();
        Map<String, Double> scores = student.getScores();
        if (courses.size() == 0) {
            throw new IllegalStateException("no courses taken");
        }
        if (courses.size() > scores.size()) {
            throw new IllegalStateException("all course`s results must be present.");
        }
        int numOfUnits = 0;
        double sum = 0, result;
        for (Course course : courses) {
            sum += scores.get(course.getCourseName()) * course.getUnitNumber();
            numOfUnits += course.getUnitNumber();
        }
        result = sum / numOfUnits;
        return result;
    }
}
