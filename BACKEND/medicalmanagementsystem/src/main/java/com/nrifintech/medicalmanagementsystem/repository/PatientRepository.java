package com.nrifintech.medicalmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrifintech.medicalmanagementsystem.model.Patient;



public interface PatientRepository extends JpaRepository<Patient, Long>{
    
}
