package com.studentm.student_management.service;

import com.studentm.student_management.model.Email;
import com.studentm.student_management.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    public Optional<Email> getEmailById(Integer id) {
        return emailRepository.findById(id);
    }

    public Email saveEmail(Email email) {
        return emailRepository.save(email);
    }

    public void deleteEmail(Integer id) {
        emailRepository.deleteById(id);
    }
}
