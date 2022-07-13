package com.behnam.school.management.college;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    boolean existsCollegeByCollegeName(String collegeName);

    @Modifying
    void deleteCollegeByCollegeName(String collegeName);
}
