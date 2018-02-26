package com.claro.moose.util.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import com.claro.moose.models.Role;
import com.claro.moose.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Gabriel Basilio Brito on 2/25/2018.
 */
@Service
public class JWTUtil {

    @Value("${jwt.signature-key}")
    private final String signatureKey = "Claro01";

    @Value("${jwt.expiration-hours}")
    private final int expirationTimeInHours = 1;

    private byte[] signatureKeyBytes;

    public JWTUtil() {
        init();
    }

    @PostConstruct
    protected void init() { signatureKeyBytes = signatureKey.getBytes(); }

    public String getToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .signWith(SignatureAlgorithm.HS512, signatureKeyBytes)
                .compressWith(CompressionCodecs.DEFLATE)
                .claim("role", user.getRole().getDescription())
                .claim("menu", user.getRole().getPermissions())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationTime())
                .compact();
    }

    public User getUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(signatureKeyBytes).parseClaimsJws(token).getBody();
        User user = new User();
        user.setName(claims.getSubject());

        Role role = new Role();
        role.setDescription(claims.get("role").toString());
        List<String> menus = (List<String>) claims.get("menu");
        role.setPermissions(menus);
        user.setRole(role);
        return user;
    }

    private Date getExpirationTime()
    {
        Date now = new Date();
        Long expireInMillis = TimeUnit.HOURS.toMillis(expirationTimeInHours);
        return new Date(expireInMillis + now.getTime());
    }
}