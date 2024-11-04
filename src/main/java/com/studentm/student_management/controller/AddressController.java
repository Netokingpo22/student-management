package com.studentm.student_management.controller;

import com.studentm.student_management.exception.BadRequestException;
import com.studentm.student_management.exception.ResourceNotFoundException;
import com.studentm.student_management.model.Address;
import com.studentm.student_management.service.AddressService;
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
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Optional<Address> address = addressService.getAddressById(id);
        if (address.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        return ResponseEntity.ok(address.get());
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        if (address.getStreet() == null) {
            throw new BadRequestException("Street is required");
        }
        return addressService.saveAddress(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address addressDetails) {
        Optional<Address> address = addressService.getAddressById(id);
        if (address.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        if (addressDetails.getStreet() == null) {
            throw new BadRequestException("Street is required");
        }
        Address updatedAddress = address.get();
        updatedAddress.setStreet(addressDetails.getStreet());
        updatedAddress.setCity(addressDetails.getCity());
        updatedAddress.setState(addressDetails.getState());
        updatedAddress.setZipCode(addressDetails.getZipCode());
        return ResponseEntity.ok(addressService.saveAddress(updatedAddress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        Optional<Address> address = addressService.getAddressById(id);
        if (address.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
