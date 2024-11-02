package com.studentm.student_management.controller;

import com.studentm.student_management.model.Phone;
import com.studentm.student_management.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<Phone> phone = phoneService.getPhoneById(id);
        return phone.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Phone createPhone(@RequestBody Phone phone) {
        return phoneService.savePhone(phone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Integer id, @RequestBody Phone phoneDetails) {
        Optional<Phone> phone = phoneService.getPhoneById(id);
        if (phone.isPresent()) {
            Phone updatedPhone = phone.get();
            updatedPhone.setPhoneNumber(phoneDetails.getPhoneNumber());
            updatedPhone.setPhoneType(phoneDetails.getPhoneType());
            updatedPhone.setCountryCode(phoneDetails.getCountryCode());
            updatedPhone.setAreaCode(phoneDetails.getAreaCode());
            return ResponseEntity.ok(phoneService.savePhone(updatedPhone));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Integer id) {
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }
}
