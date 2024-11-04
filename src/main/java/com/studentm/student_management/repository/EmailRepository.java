package com.studentm.student_management.repository;

import com.studentm.student_management.model.Email;
import com.studentm.student_management.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
}
