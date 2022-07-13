package com.behnam.school.management.course;

import com.behnam.school.management.college.College;
import com.behnam.school.management.professor.Professor;
import com.behnam.school.management.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(updatable = false)
    private Long courseId;
    @Column(nullable = false, length = 20, unique = true)
    private String courseName;
    @Column(nullable = false)
    private Integer unitNumber;


    @ManyToOne(
            cascade = CascadeType.MERGE,
            optional = false
    )
    @JoinColumn(
            name = "professor_id",
            referencedColumnName = "professorId"
    )
    @JsonIgnore
    private Professor professor;


    @ManyToOne(
            cascade = CascadeType.MERGE,
            optional = false
    )
    @JoinColumn(
            name = "college_id",
            referencedColumnName = "collegeId"
    )
    @JsonIgnore
    private College courseCollege;

    @ManyToMany(mappedBy = "studentCourses", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Student> enrolled_students;

    public Course() {
    }

    public Course(String courseName, Integer unitNumber, Professor professor, College courseCollege, List<Student> enrolled_students) {
        this.courseName = courseName;
        this.unitNumber = unitNumber;
        this.professor = professor;
        this.courseCollege = courseCollege;
        this.enrolled_students = enrolled_students;
    }



    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public College getCourseCollege() {
        return courseCollege;
    }

    public void setCourseCollege(College college) {
        this.courseCollege = college;
    }

    public List<Student> getEnrolled_students() {
        return enrolled_students;
    }

    public void setEnrolled_students(List<Student> enrolled_students) {
        this.enrolled_students = enrolled_students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", unitNumber=" + unitNumber +
                ", professor=" + professor +
                ", courseCollege=" + courseCollege +
                ", enrolled_students=" + enrolled_students +
                '}';
    }
}
