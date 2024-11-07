package com.studentm.student_management.controller;

import com.studentm.student_management.dto.StudentDTO;
import com.studentm.student_management.exception.BadRequestException;
import com.studentm.student_management.exception.ResourceNotFoundException;
import com.studentm.student_management.model.Student;
import com.studentm.student_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        return ResponseEntity.ok(student.get());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        if (student.getLastName() == null || student.getGender() == null) {
            throw new BadRequestException("Last name and gender are required");
        }
        return studentService.saveStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (studentDetails.getLastName() == null || studentDetails.getGender() == null) {
            throw new BadRequestException("Last name and gender are required");
        }
        Student updatedStudent = student.get();
        updatedStudent.setFirstName(studentDetails.getFirstName());
        updatedStudent.setLastName(studentDetails.getLastName());
        updatedStudent.setMiddleName(studentDetails.getMiddleName());
        updatedStudent.setGender(studentDetails.getGender());
        return ResponseEntity.ok(studentService.saveStudent(updatedStudent));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
