package com.studentm.student_management.model;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailId implements Serializable {
    private String email;
    private Integer student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailId emailId = (EmailId) o;
        return Objects.equals(email, emailId.email) && Objects.equals(student, emailId.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, student);
    }
}