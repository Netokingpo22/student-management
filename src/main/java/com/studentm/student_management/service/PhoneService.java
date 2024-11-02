package com.studentm.student_management.service;

import com.studentm.student_management.model.Phone;
import com.studentm.student_management.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    public Optional<Phone> getPhoneById(Integer phoneId) {
        return phoneRepository.findById(phoneId);
    }

    public Phone savePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    public void deletePhone(Integer phoneId) {
        phoneRepository.deleteById(phoneId);
    }
}
