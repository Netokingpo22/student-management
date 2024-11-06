package com.studentm.student_management.service;

import com.studentm.student_management.dto.StudentDTO;
import com.studentm.student_management.model.Student;
import com.studentm.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapperService studentMapperService;

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapperService::toStudentDTO).collect(Collectors.toList());
    }

    public Optional<Student> getStudentById(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }

    public Page<Student> searchStudents(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return studentRepository.findAll(pageable);
        }
        return studentRepository.searchStudents(searchTerm.trim(), pageable);
    }
}
