package com.nrifintech.medicalmanagementsystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrifintech.medicalmanagementsystem.model.Doctor;
import com.nrifintech.medicalmanagementsystem.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Doctor addDoctor(Doctor doctor){
        return adminRepository.save(doctor);
    }
}
