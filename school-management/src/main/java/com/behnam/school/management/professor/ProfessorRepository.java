package com.behnam.school.management.professor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("select p from Professor p where p.nationalId=?1")
    Optional<Professor> findProfessorByNationalId(Long nationalId);

    @Query("select p from Professor p where p.personalId=?1")
    Optional<Professor> findProfessorByPersonalId(Long personalId);

    List<Professor> findProfessorByFirstName(String firstName);

}
