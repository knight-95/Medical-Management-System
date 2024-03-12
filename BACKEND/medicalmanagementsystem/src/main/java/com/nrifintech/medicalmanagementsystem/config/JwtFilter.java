package com.nrifintech.medicalmanagementsystem.config;


import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.nrifintech.medicalmanagementsystem.service.DefaultUserService;
import com.nrifintech.medicalmanagementsystem.service.GenerateResponseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;


public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	DefaultUserService defaultUserService;


	private HandlerExceptionResolver exceptionResolver;

	@Autowired
	GenerateResponseService generateResponseService;

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	JwtGeneratorValidator jwtgenVal;

	@Autowired
    public JwtFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    public JwtFilter() {}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		String token = null;
		String userName = null;
		
		// if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		// 	token = authorizationHeader.substring(7);
			// userName = jwtgenVal.extractUsername(token);
			// try {

                // userName = jwtgenVal.extractUsername(token);

            // } catch (IllegalArgumentException e) {
            //     generateResponseService.generateResponse("Illegal",HttpStatusCode.valueOf(403));
			// 	filterChain.doFilter(request, response);
            // } catch (ExpiredJwtException e) {
            //     //logger.info("Given jwt token is expired !!");
            //     generateResponseService.generateResponse("Illegal",HttpStatusCode.valueOf(403));
            // } catch (MalformedJwtException e) {
            //     //logger.info("Some changed has been done in token !! Invalid Token");
			// 	generateResponseService.generateResponse("Illegal",HttpStatusCode.valueOf(403));
            // } catch (Exception e) {
                
				//e.printStackTrace();
            // }
		// }
		// else {
        //     logger.info("Invalid Header Value !! ");
        // }
		// if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		// 	token = authorizationHeader.substring(7);
		// 	// userName = jwtgenVal.extractUsername(token);

        //         userName = jwtgenVal.extractUsername(token);
		// }
		// else {
        //     logger.info("Invalid Header Value !! ");
        // }
try{
	if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			userName = jwtgenVal.extractUsername(token);
	}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = defaultUserService.loadUserByUsername(userName);

			if (jwtgenVal.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtgenVal.getAuthenticationToken(token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
                logger.info("Validation failed!!");
            }
		}
		filterChain.doFilter(request, response);
	}
	catch(ExpiredJwtException | SignatureException ex)
	{
		exceptionResolver.resolveException(request, response, null, ex);
	}
	
	
	
	
}
}
