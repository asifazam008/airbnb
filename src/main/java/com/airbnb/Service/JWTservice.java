package com.airbnb.Service;

import com.airbnb.Entity.PropertyUser;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.security.config.Elements.JWT;

@Service
public class JWTservice {
@Value("${jwt.algorithm.key}")
    private String algorithmKey;
@Value("${jwt.issuer}")
    private String issuer;
@Value("${jwt.expiry.time}")
    private int expiryTime;
private final static  String USER_NAME="username";
    private Algorithm algorithm;
    @PostConstruct 
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }
    public String generateToken(PropertyUser propertyUser){//ceis
       return com.auth0.jwt.JWT.create().withClaim(USER_NAME,propertyUser.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)).withIssuer(issuer)
                .sign(algorithm);
    }
    public String getUserName(String token){
        DecodedJWT decodeJwt = com.auth0.jwt.JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodeJwt.getClaim(USER_NAME).asString();
    }

}
