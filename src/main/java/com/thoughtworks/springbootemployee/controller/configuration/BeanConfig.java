package com.thoughtworks.springbootemployee.controller.configuration;

import com.thoughtworks.springbootemployee.controller.CompanyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public CompanyRepository getCompanyRepository(){
        return new CompanyRepository();
    }
}
