package com.studentm.student_management;

import com.studentm.student_management.model.Address;
import com.studentm.student_management.model.Student;
import com.studentm.student_management.repository.AddressRepository;
import com.studentm.student_management.service.AddressService;
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
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setStudentId(1);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGender(Student.Gender.MALE);

        address = new Address();
        address.setAddressId(1);
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        address.setState("IL");
        address.setStudent(student);
    }

    @Test
    public void testGetAllAddresses() {
        List<Address> addresses = Arrays.asList(address);
        when(addressRepository.findAll()).thenReturn(addresses);

        List<Address> result = addressService.getAllAddresses();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    public void testGetAddressById() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        Optional<Address> result = addressService.getAddressById(1);

        assertTrue(result.isPresent());
        assertEquals(address.getStreet(), result.get().getStreet());
        verify(addressRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveAddress() {
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.saveAddress(address);

        assertNotNull(result);
        assertEquals(address.getStreet(), result.getStreet());
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void testDeleteAddress() {
        doNothing().when(addressRepository).deleteById(1);

        addressService.deleteAddress(1);

        verify(addressRepository, times(1)).deleteById(1);
    }
}
