package com.studentm.student_management.controller;

import com.studentm.student_management.model.Email;
import com.studentm.student_management.model.EmailId;
import com.studentm.student_management.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Email> getAllEmails() {
        return emailService.getAllEmails();
    }

    @GetMapping("/{email}/{studentId}")
    public ResponseEntity<Email> getEmailById(@PathVariable String email, @PathVariable Integer studentId) {
        EmailId emailId = new EmailId(email, studentId);
        Optional<Email> emailOptional = emailService.getEmailById(emailId);
        return emailOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Email createEmail(@RequestBody Email email) {
        return emailService.saveEmail(email);
    }

    @PutMapping("/{email}/{studentId}")
    public ResponseEntity<Email> updateEmail(@PathVariable String email, @PathVariable Integer studentId, @RequestBody Email emailDetails) {
        EmailId emailId = new EmailId(email, studentId);
        Optional<Email> emailOptional = emailService.getEmailById(emailId);
        if (emailOptional.isPresent()) {
            Email updatedEmail = emailOptional.get();
            updatedEmail.setEmailType(emailDetails.getEmailType());
            return ResponseEntity.ok(emailService.saveEmail(updatedEmail));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{email}/{studentId}")
    public ResponseEntity<Void> deleteEmail(@PathVariable String email, @PathVariable Integer studentId) {
        EmailId emailId = new EmailId(email, studentId);
        emailService.deleteEmail(emailId);
        return ResponseEntity.noContent().build();
    }
}
