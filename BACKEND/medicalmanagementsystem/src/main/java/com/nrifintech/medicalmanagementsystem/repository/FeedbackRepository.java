package com.nrifintech.medicalmanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrifintech.medicalmanagementsystem.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
    
}
