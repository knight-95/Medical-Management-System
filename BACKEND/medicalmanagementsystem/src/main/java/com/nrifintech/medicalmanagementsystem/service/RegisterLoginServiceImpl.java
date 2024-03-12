package com.nrifintech.medicalmanagementsystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import com.nrifintech.medicalmanagementsystem.config.JwtGeneratorValidator;
import com.nrifintech.medicalmanagementsystem.dto.JwtAuthenticationResponse;
import com.nrifintech.medicalmanagementsystem.dto.RefreshTokenRequest;
import com.nrifintech.medicalmanagementsystem.dto.UserDTO;
import com.nrifintech.medicalmanagementsystem.model.Role;
import com.nrifintech.medicalmanagementsystem.model.User;
import com.nrifintech.medicalmanagementsystem.repository.RoleRepository;
import com.nrifintech.medicalmanagementsystem.repository.UserRepository;

@Service
public class RegisterLoginServiceImpl implements RegisterLoginService{
    
@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtGeneratorValidator jwtGeneratorValidator;

    @Autowired
    DefaultUserServiceImpl userDetailService;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
	public User save(UserDTO userRegisteredDTO) {						// register admin and user using DTO
		Role role = new Role();
		if(userRegisteredDTO.getRole().equals("PATIENT"))
		  role = roleRepo.findByRoleName("ROLE_PATIENT");
		else if(userRegisteredDTO.getRole().equals("DOCTOR"))
		  role = roleRepo.findByRoleName("ROLE_DOCTOR");
		else if(userRegisteredDTO.getRole().equals("ADMIN"))
		 role = roleRepo.findByRoleName("ROLE_ADMIN");
		User user = new User();
		//user.setEmail(userRegisteredDTO.getEmail());
		user.setUserName(userRegisteredDTO.getUserName());
		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRole(role);
		System.out.println("HELLO"+user.getPassword());
		System.out.println("HELLO"+user.getRole());

		System.out.println("HELLO"+user.getUserName());
		return userRepo.save(user);
	}


	public JwtAuthenticationResponse login(@RequestBody UserDTO userDto) throws Exception{

		System.out.println("SECOND"+userDto.getPassword()+"S"+userDto.getUserName());
		try{
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(authentication.getName());
			String jwt = jwtGeneratorValidator.generateToken(authentication);
            System.out.println("OVVVVVVER HEEEEEEERRRRREEE111");
			String jwtRefresh = jwtGeneratorValidator.generateRefreshToken(authentication);
            System.out.println("OVVVVVVER HEEEEEEERRRRREEE11");
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(jwtRefresh);
			return jwtAuthenticationResponse;
		} catch(BadCredentialsException e){
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
        catch(Exception e)
        {
           //e.printStackTrace();
            throw e;
        }
	}

	


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getToken();
        String username = jwtGeneratorValidator.extractUsername(refreshToken);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (jwtGeneratorValidator.validateToken(refreshToken,userDetails)) {
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtGeneratorValidator.generateToken(authentication);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            return jwtAuthenticationResponse;
        } else {
            throw new BadCredentialsException("Please Login again !!");
        }
    }


    // @ExceptionHandler(BadCredentialsException.class)
    // public String exceptionHandler() {
    //     return "Credentials Invalid !!";
    // }

    }