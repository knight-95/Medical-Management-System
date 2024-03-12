package com.nrifintech.medicalmanagementsystem.controller;

<<<<<<< HEAD

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> 50b91a0552d431f5c310123a2756ee685142c4dc
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrifintech.medicalmanagementsystem.model.Doctor;
<<<<<<< HEAD
import com.nrifintech.medicalmanagementsystem.service.AdminService;
=======
import com.nrifintech.medicalmanagementsystem.service.DoctorService;
>>>>>>> 50b91a0552d431f5c310123a2756ee685142c4dc

@RestController
@RequestMapping("admin")
public class AdminController {
<<<<<<< HEAD
    @Autowired
    private AdminService adminService;

    @PostMapping("/adddoctor")
    public ResponseEntity<Object> addDoctor(@RequestBody Doctor doctor) {
        if (doctor.getSpecialization() == null || doctor.getSpecialization().getId() == null) {
            return ResponseEntity.badRequest().body("Specialization is required");
        }
    
        // You can perform additional validation if needed
    
        Doctor createdDoctor = adminService.addDoctor(doctor);
        return ResponseEntity.ok(createdDoctor);
    }
    

    @PostMapping("/test")
    public String test(){
        return "hello";
    }

}
=======

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add-doctor")
    public ResponseEntity<Object> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.addDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }


    @PostMapping("/test")
    public String testDoctor()
    {
        return "Admin api working!";
    }
}
>>>>>>> 50b91a0552d431f5c310123a2756ee685142c4dc
