package com.studentm.student_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {

    private Integer studentId;
    private String fullName;
    private String gender;
    private boolean hasEmails;
    private boolean hasAddresses;
    private boolean hasPhones;
}
