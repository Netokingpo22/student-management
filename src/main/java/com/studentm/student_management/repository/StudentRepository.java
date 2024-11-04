package com.studentm.student_management.repository;

import com.studentm.student_management.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(CONCAT(s.firstName, ' ', s.middleName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Student> searchStudents(@Param("searchTerm") String searchTerm, Pageable pageable);
}
