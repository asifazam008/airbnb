package com.airbnb.Config;


import com.airbnb.Entity.PropertyUser;
import com.airbnb.Service.JWTservice;
import com.airbnb.repository.PropertyUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
    @Autowired
    private JWTservice jwTservice;
    @Autowired
    private PropertyUserRepository propertyUserRepository;

    JwtRequestFilter(){
    }
    public JwtRequestFilter(PropertyUserRepository propertyUserRepository) {
        this.propertyUserRepository = propertyUserRepository;
    }
    public JwtRequestFilter(JWTservice jwTservice) {
        this.jwTservice = jwTservice;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=request.getHeader("Authorization");
        if(token !=null&& token.startsWith("Bearer ")){
           String tokenhead=token.substring(8,token.length()-1);
            String userName = jwTservice.getUserName(tokenhead);
            Optional<PropertyUser> byUserName =  propertyUserRepository.findByUserName(userName);
            if(byUserName.isPresent()){
                PropertyUser user =                                byUserName.get();
                //help me to keep track of current user login
                UsernamePasswordAuthenticationToken authentication=
                        new UsernamePasswordAuthenticationToken(user,null, Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole())));
            authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        }
        filterChain.doFilter(request,response);
    }
}
