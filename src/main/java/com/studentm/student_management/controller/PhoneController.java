package com.studentm.student_management.controller;

import com.studentm.student_management.exception.BadRequestException;
import com.studentm.student_management.exception.ResourceNotFoundException;
import com.studentm.student_management.model.Phone;
import com.studentm.student_management.service.PhoneService;
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
@RequestMapping("/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public List<Phone> getAllPhones() {
        return phoneService.getAllPhones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable Integer id) {
        Optional<Phone> phoneOptional = phoneService.getPhoneById(id);
        if (phoneOptional.isEmpty()) {
            throw new ResourceNotFoundException("Phone not found with id: " + id);
        }
        return ResponseEntity.ok(phoneOptional.get());
    }

    @PostMapping
    public Phone createPhone(@RequestBody Phone phone) {
        if (phone.getPhoneNumber() == null || phone.getPhoneType() == null) {
            throw new BadRequestException("Phone number and phone type are required");
        }
        return phoneService.savePhone(phone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Integer id, @RequestBody Phone phoneDetails) {
        Optional<Phone> phoneOptional = phoneService.getPhoneById(id);
        if (phoneOptional.isEmpty()) {
            throw new ResourceNotFoundException("Phone not found with id: " + id);
        }
        if (phoneDetails.getPhoneNumber() == null || phoneDetails.getPhoneType() == null) {
            throw new BadRequestException("Phone number and phone type are required");
        }
        Phone updatedPhone = phoneOptional.get();
        updatedPhone.setPhoneNumber(phoneDetails.getPhoneNumber());
        updatedPhone.setPhoneType(phoneDetails.getPhoneType());
        updatedPhone.setCountryCode(phoneDetails.getCountryCode());
        updatedPhone.setAreaCode(phoneDetails.getAreaCode());
        return ResponseEntity.ok(phoneService.savePhone(updatedPhone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Integer id) {
        Optional<Phone> phoneOptional = phoneService.getPhoneById(id);
        if (phoneOptional.isEmpty()) {
            throw new ResourceNotFoundException("Phone not found with id: " + id);
        }
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }
}
