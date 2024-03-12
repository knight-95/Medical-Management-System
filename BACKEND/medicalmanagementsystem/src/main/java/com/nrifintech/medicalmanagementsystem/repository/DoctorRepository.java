package com.nrifintech.medicalmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrifintech.medicalmanagementsystem.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    
}
