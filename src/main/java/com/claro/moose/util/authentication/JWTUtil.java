package com.claro.moose.util.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import com.claro.moose.models.Permission;
import com.claro.moose.models.Role;
import com.claro.moose.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Gabriel Basilio Brito on 2/25/2018.
 */
@Component
public class JWTUtil {

    @Value("${jwt.signature-key}")
    private String signatureKey;

    @Value("${jwt.expiration-hours}")
    private int expirationTimeInHours;

    private byte[] signatureKeyBytes;

    @PostConstruct
    protected void init() {
        signatureKeyBytes = signatureKey.getBytes();
    }

    public String getToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .signWith(SignatureAlgorithm.HS512, signatureKeyBytes)
              //  .compressWith(CompressionCodecs.DEFLATE)
                .claim("role", user.getRole().getDescription())
                .claim("permissions", user.getRole().getPermissions())
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
        List<Permission> menus = (List<Permission>) claims.get("permissions");
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