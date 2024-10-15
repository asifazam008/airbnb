package com.airbnb.Config;

import com.airbnb.Payload.PropertyUserDto;
import com.airbnb.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter;
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
    http.csrf().disable().cors().disable();
    http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
    http.authorizeHttpRequests().anyRequest().permitAll();
//            .requestMatchers("/api/v1/user/addUser","/api/v1/user/login").
//            permitAll().
//            requestMatchers("/api/v1/country/addCounty") .
//            hasRole("ADMIN")
//            .anyRequest().authenticated();
    return http.build();
}
}
