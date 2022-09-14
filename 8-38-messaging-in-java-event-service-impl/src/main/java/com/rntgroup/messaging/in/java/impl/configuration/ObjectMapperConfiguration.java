package com.rntgroup.messaging.in.java.impl.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {
    
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
}
