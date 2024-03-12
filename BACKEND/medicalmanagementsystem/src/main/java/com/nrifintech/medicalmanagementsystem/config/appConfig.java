package com.nrifintech.medicalmanagementsystem.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

@Configuration
public class appConfig {

    @Value("${app.deployment.date}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @Bean
    public LocalDate getDeploymentDate() {
        return startDate;
    }
    
}
