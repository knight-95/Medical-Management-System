package com.nrifintech.medicalmanagementsystem.model;

import java.util.UUID;

import org.springframework.core.annotation.Order;

import com.nrifintech.medicalmanagementsystem.utility.enums.Rating;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Order(7)
@Data
@Entity
@Table(name="Feedback")
public class Feedback {


    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
@SequenceGenerator(name = "feedback_seq", sequenceName = "FEEDBACK_SEQ", allocationSize = 1)
    private Long id;
    // @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    // private String id;

     @OneToOne
     @JoinColumn(name = "appointment_id", unique = true, nullable=false)
     private Appointment appointment;

   


     @NotNull
     @Max(value=5, message="Cannot be more than 5")
     @Min(value=1, message="Cannot be less than 1")
     @Column(nullable=false, length=2, columnDefinition = "INT DEFAULT 5")
     private Integer rating;

    @Column(nullable=true, length=100)
    private String review;
    
}
