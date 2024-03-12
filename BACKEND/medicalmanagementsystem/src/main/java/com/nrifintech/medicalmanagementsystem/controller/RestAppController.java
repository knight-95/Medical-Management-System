package com.nrifintech.medicalmanagementsystem.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nrifintech.medicalmanagementsystem.config.JwtGeneratorValidator;
import com.nrifintech.medicalmanagementsystem.dto.RefreshTokenRequest;
import com.nrifintech.medicalmanagementsystem.dto.UserDTO;
import com.nrifintech.medicalmanagementsystem.model.User;
import com.nrifintech.medicalmanagementsystem.repository.UserRepository;
import com.nrifintech.medicalmanagementsystem.service.GenerateResponseService;
import com.nrifintech.medicalmanagementsystem.service.RegisterLoginService;

@RestController
public class RestAppController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtGeneratorValidator jwtGenVal;
	
	@Autowired
	BCryptPasswordEncoder bcCryptPasswordEncoder;
	
	@Autowired
	RegisterLoginService registerLoginService;

	@Autowired
	GenerateResponseService generateResponseService;

	@PostMapping("/auth/registration")
	public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDto) {
		User users =  registerLoginService.save(userDto);
		if (users.equals(null))
			return generateResponseService.generateResponse("Not able to save user ", HttpStatus.BAD_REQUEST, userDto);
		else
			return generateResponseService.generateResponse("User saved successfully : " + users.getId(), HttpStatus.OK, users);
	}

	@GetMapping("/auth/login")
	public ResponseEntity<Object> generateJwtToken(@RequestBody UserDTO userDto) throws Exception{
		return generateResponseService.generateResponse("Login Successful!", HttpStatus.OK, registerLoginService.login(userDto));
		
	}

	@GetMapping("/auth/refresh")
	public ResponseEntity<Object> refreshJwtToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception{
		return generateResponseService.generateResponse("Login Successful!", HttpStatus.OK, registerLoginService.refreshToken(refreshTokenRequest));
		
	}

	
	@GetMapping("/welcomeAdmin")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String welcome(Principal p) {
		return "WelcomeAdmin "+p.getName();
	}

	@GetMapping("/welcomeDoctor")
	// @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String welcomeDoctor(Principal p) {
		return "Welcome Doctor "+p.getName();
	}
	@GetMapping("/welcomePatient")
	// @PreAuthorize("hasAuthority('ROLE_PATIENT')")
	public String welcomePatient(Principal p) {
		return "Welcome Patient "+p.getName();
	}

	//  @ExceptionHandler(BadCredentialsException.class)
    // public String exceptionHandler() {
    //     return "Credentials Invalid !!";
    // }
	
	// public ResponseEntity<Object> generateRespose(String message, HttpStatus st, Object responseobj) {

	// 	Map<String, Object> map = new HashMap<String, Object>();
	// 	map.put("meaasge", message);
	// 	map.put("Status", st.value());
	// 	// map.put("data", responseobj);
	// 	map.put("cookies", responseobj);
	// 	return new ResponseEntity<Object>(map, st);
	// }

}
