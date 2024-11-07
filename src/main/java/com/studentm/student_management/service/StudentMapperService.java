package com.studentm.student_management.service;

import com.studentm.student_management.dto.StudentDTO;
import com.studentm.student_management.model.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentMapperService {

    public StudentDTO toStudentDTO(Student student) {
        String firstName = student.getFirstName() != null ? student.getFirstName() : "";
        String middleName = student.getMiddleName() != null ? student.getMiddleName() : "";
        String lastName = student.getLastName() != null ? student.getLastName() : "";
        String fullName = String.format("%s %s %s", firstName, middleName, lastName).trim().replaceAll(" +", " ");
        return new StudentDTO(
            student.getStudentId(),
            fullName,
            student.getEmails() != null && !student.getEmails().isEmpty(),
            student.getAddresses() != null && !student.getAddresses().isEmpty(),
            student.getPhones() != null && !student.getPhones().isEmpty()
        );
    }
}
