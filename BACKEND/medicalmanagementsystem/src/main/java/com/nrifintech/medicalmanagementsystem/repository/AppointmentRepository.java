package com.nrifintech.medicalmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrifintech.medicalmanagementsystem.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}
