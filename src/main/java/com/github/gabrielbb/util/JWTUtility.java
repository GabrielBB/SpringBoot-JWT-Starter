package com.github.gabrielbb.util;

import com.github.gabrielbb.models.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

/**
 * Created by Gabriel Basilio Brito on 3/13/2018.
 */
@Component
public class JWTUtility {

    @Value("${jwt.expiration-hours}")
    private int expirationHours;

    @Value("${jwt.signature-key}")
    private String signatureKey;

    private byte[] signatureKeyBytes;

    @PostConstruct
    public void init() {
        this.signatureKeyBytes = signatureKey.getBytes();
    }

    public String getToken(User user) { 
        return Jwts.builder().setSubject(user.getId().toString()) // Subject is the unique identifier, this is usually an user ID or unique username
                .signWith(SignatureAlgorithm.HS512, signatureKeyBytes)
                .claim("name", user.getName())
                .claim("role", user.getRole()) // Add more claims according to your model class
                .setIssuedAt(new Date())
                .setExpiration(getExpirationTime())
                .compact();
    }

    public User getUser(String token) throws SignatureException {
        Claims claims = Jwts.parser()
        .setSigningKey(signatureKeyBytes)
        .parseClaimsJws(token)
        .getBody();

        User user = new User();
        user.setId(Integer.valueOf(claims.getSubject()));
        user.setName(claims.get("name").toString());
        user.setRole(claims.get("role").toString());

        return user;
    }

    private Date getExpirationTime() {
        Long expireInMillis = TimeUnit.HOURS.toMillis(expirationHours);
        return new Date(expireInMillis + new Date().getTime());
    }
}