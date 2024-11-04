package com.studentm.student_management;

import com.studentm.student_management.model.Student;
import com.studentm.student_management.repository.StudentRepository;
import com.studentm.student_management.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setStudentId(1);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGender(Student.Gender.MALE);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(1);

        assertTrue(result.isPresent());
        assertEquals(student.getLastName(), result.get().getLastName());
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertNotNull(result);
        assertEquals(student.getLastName(), result.getLastName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1);

        studentService.deleteStudent(1);

        verify(studentRepository, times(1)).deleteById(1);
    }

    @Test
    public void testSearchStudents() {
        Page<Student> studentPage = new PageImpl<>(Arrays.asList(student));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        when(studentRepository.searchStudents(anyString(), eq(pageable))).thenReturn(studentPage);

        Page<Student> result = studentService.searchStudents("John", 0, 10, "firstName", "ASC");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(studentRepository, times(1)).searchStudents(anyString(), eq(pageable));
    }
}
