package com.nrifintech.medicalmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nrifintech.medicalmanagementsystem.model.Doctor;

@Repository
public interface AdminRepository extends JpaRepository<Doctor, Long> {

    
} 
