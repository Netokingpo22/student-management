package com.studentm.student_management.repository;

import com.studentm.student_management.model.Email;
import com.studentm.student_management.model.EmailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, EmailId> {
}
