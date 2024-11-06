package com.studentm.student_management;

import com.studentm.student_management.model.Email;
import com.studentm.student_management.model.Student;
import com.studentm.student_management.repository.EmailRepository;
import com.studentm.student_management.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;
    @InjectMocks
    private EmailService emailService;
    private Email email;
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setStudentId(1);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGender(Student.Gender.MALE);
        email = new Email();
        email.setEmailId(1);
        email.setEmail("test@example.com");
        email.setEmailType(Email.EmailType.PERSONAL);
        email.setStudent(student);
    }

    @Test
    public void testGetAllEmails() {
        List<Email> emails = Arrays.asList(email);
        when(emailRepository.findAll()).thenReturn(emails);
        List<Email> result = emailService.getAllEmails();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(emailRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmailById() {
        when(emailRepository.findById(1)).thenReturn(Optional.of(email));
        Optional<Email> result = emailService.getEmailById(1);
        assertTrue(result.isPresent());
        assertEquals(email.getEmail(), result.get().getEmail());
        verify(emailRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveEmail() {
        when(emailRepository.save(email)).thenReturn(email);
        Email result = emailService.saveEmail(email);
        assertNotNull(result);
        assertEquals(email.getEmail(), result.getEmail());
        verify(emailRepository, times(1)).save(email);
    }

    @Test
    public void testDeleteEmail() {
        doNothing().when(emailRepository).deleteById(1);
        emailService.deleteEmail(1);
        verify(emailRepository, times(1)).deleteById(1);
    }
}
