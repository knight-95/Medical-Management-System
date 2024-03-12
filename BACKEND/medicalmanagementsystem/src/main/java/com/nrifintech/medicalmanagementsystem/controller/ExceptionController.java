package com.nrifintech.medicalmanagementsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nrifintech.medicalmanagementsystem.service.GenerateResponseService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class ExceptionController {

    @Autowired
	GenerateResponseService generateResponseService;
    

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException e) {
        
        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        
        return generateResponseService.generateResponse("Invalid Username or Password !!", HttpStatusCode.valueOf(401));
    }


@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleSecurityException(Exception ex) {
        ResponseEntity<Object> errorDetail = null;

        if (ex instanceof AccessDeniedException) {

            return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(403));

            // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            // errorDetail.setProperty("access_denied_reason", "not_authorized!");

        }

        if (ex instanceof SignatureException) {

            return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(403));

            // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            // errorDetail.setProperty("access_denied_reason", "JWT Signature not valid");
        }
        if (ex instanceof ExpiredJwtException) {
            // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            // errorDetail.setProperty("access_denied_reason", "JWT Token already expired !");

            return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(403));
        }

        return errorDetail;
    }


    
}

