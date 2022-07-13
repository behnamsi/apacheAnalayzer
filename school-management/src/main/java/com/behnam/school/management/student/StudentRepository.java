package com.behnam.school.management.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student  s where s.universityId=?1")
    Optional<Student> findStudentByUniversityIdOptional(Long universityId);

    @Query("select s from Student  s where s.nationalId=?1")
    Optional<Student> findStudentByNationalId(Long nationalId);


    Student findStudentByUniversityId(Long studentId);
    @Modifying
    void deleteStudentByUniversityId(Long universityId);


    boolean existsByUniversityId(Long uniID);

}

