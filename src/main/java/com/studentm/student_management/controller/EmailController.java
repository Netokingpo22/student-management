package com.studentm.student_management.controller;

import com.studentm.student_management.exception.BadRequestException;
import com.studentm.student_management.exception.ResourceNotFoundException;
import com.studentm.student_management.model.Email;
import com.studentm.student_management.service.EmailService;
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
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Email> getAllEmails() {
        return emailService.getAllEmails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Email> getEmailById(@PathVariable Integer id) {
        Optional<Email> emailOptional = emailService.getEmailById(id);
        if (emailOptional.isEmpty()) {
            throw new ResourceNotFoundException("Email not found with id: " + id);
        }
        return ResponseEntity.ok(emailOptional.get());
    }

    @PostMapping
    public Email createEmail(@RequestBody Email email) {
        if (email.getEmail() == null || email.getEmailType() == null) {
            throw new BadRequestException("Email and email type are required");
        }
        return emailService.saveEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Email> updateEmail(@PathVariable Integer id, @RequestBody Email emailDetails) {
        Optional<Email> emailOptional = emailService.getEmailById(id);
        if (emailOptional.isEmpty()) {
            throw new ResourceNotFoundException("Email not found with id: " + id);
        }
        if (emailDetails.getEmail() == null || emailDetails.getEmailType() == null) {
            throw new BadRequestException("Email and email type are required");
        }
        Email updatedEmail = emailOptional.get();
        updatedEmail.setEmailType(emailDetails.getEmailType());
        updatedEmail.setEmail(emailDetails.getEmail());
        return ResponseEntity.ok(emailService.saveEmail(updatedEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Integer id) {
        Optional<Email> emailOptional = emailService.getEmailById(id);
        if (emailOptional.isEmpty()) {
            throw new ResourceNotFoundException("Email not found with id: " + id);
        }
        emailService.deleteEmail(id);
        return ResponseEntity.noContent().build();
    }
}
