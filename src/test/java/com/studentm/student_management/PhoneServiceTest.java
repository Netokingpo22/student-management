package com.studentm.student_management;

import com.studentm.student_management.model.Phone;
import com.studentm.student_management.model.Student;
import com.studentm.student_management.repository.PhoneRepository;
import com.studentm.student_management.service.PhoneService;
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
public class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneService phoneService;

    private Phone phone;
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setStudentId(1);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGender(Student.Gender.MALE);

        phone = new Phone();
        phone.setPhoneId(1);
        phone.setPhoneNumber("123-456-7890");
        phone.setPhoneType(Phone.PhoneType.MOBILE);
        phone.setStudent(student);
    }

    @Test
    public void testGetAllPhones() {
        List<Phone> phones = Arrays.asList(phone);
        when(phoneRepository.findAll()).thenReturn(phones);

        List<Phone> result = phoneService.getAllPhones();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(phoneRepository, times(1)).findAll();
    }

    @Test
    public void testGetPhoneById() {
        when(phoneRepository.findById(1)).thenReturn(Optional.of(phone));

        Optional<Phone> result = phoneService.getPhoneById(1);

        assertTrue(result.isPresent());
        assertEquals(phone.getPhoneNumber(), result.get().getPhoneNumber());
        verify(phoneRepository, times(1)).findById(1);
    }

    @Test
    public void testSavePhone() {
        when(phoneRepository.save(phone)).thenReturn(phone);

        Phone result = phoneService.savePhone(phone);

        assertNotNull(result);
        assertEquals(phone.getPhoneNumber(), result.getPhoneNumber());
        verify(phoneRepository, times(1)).save(phone);
    }

    @Test
    public void testDeletePhone() {
        doNothing().when(phoneRepository).deleteById(1);

        phoneService.deletePhone(1);

        verify(phoneRepository, times(1)).deleteById(1);
    }
}
