package com.behnam.school.management.student;

import com.behnam.school.management.college.College;
import com.behnam.school.management.course.Course;
import com.behnam.school.management.professor.Professor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table()
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(updatable = false)
    private Long studentId;
    @Column(nullable = false, length = 15)
    private String firstName;
    @Column(nullable = false, length = 25)
    private String lastName;
    @Column(nullable = false, unique = true)
    private Long nationalId;
    @Column(nullable = false, unique = true)
    private Long universityId;

    // studentCollege
    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "college_id",
            referencedColumnName = "collegeId"
    )
    @JsonIgnore
    private College studentCollege;

    // studentCourses
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id",
                    referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "course_id",
                    referencedColumnName = "courseId")
    )
    @JsonIgnore
    private List<Course> studentCourses;

    // professorsOfStudent
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "student_professor",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "professorId")
    )
    @JsonIgnore
    private List<Professor> professorsOfStudent;

    @ElementCollection
    private Map<String, Double> scores = new HashMap<>();

    // constructor
    public Student() {
    }

    public Student(String firstName, String lastName, Long nationalId, Long universityId,
                   College studentCollege, List<Course> studentCourses,
                   List<Professor> professorsOfStudent, Map<String, Double> scores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.universityId = universityId;
        this.studentCollege = studentCollege;
        this.studentCourses = studentCourses;
        this.professorsOfStudent = professorsOfStudent;
        this.scores = scores;
    }

    public Map<String, Double> getScores() {
        return scores;
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public College getStudentCollege() {
        return studentCollege;
    }

    public void setStudentCollege(College studentCollege) {
        this.studentCollege = studentCollege;
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public List<Professor> getProfessorsOfStudent() {
        return professorsOfStudent;
    }

    public void setProfessorsOfStudent(List<Professor> professorsOfStudent) {
        this.professorsOfStudent = professorsOfStudent;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId=" + nationalId +
                ", universityId=" + universityId +
                ", studentCollege=" + studentCollege +
                ", studentCourses=" + studentCourses +
                ", professorsOfStudent=" + professorsOfStudent +
                '}';
    }
}
