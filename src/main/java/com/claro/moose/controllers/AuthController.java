package com.claro.moose.controllers;

import com.claro.moose.models.Role;
import com.claro.moose.models.User;
import com.claro.moose.util.authentication.JWTUtil;
import com.claro.moose.util.authentication.PAWClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PAWClient pawClient;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "usr") String username,
            @RequestParam(name = "token") long token) {

        if (pawClient.isValidToken(username, token)) {
            User user = new User();
            user.setName(username);

            Role role = pawClient.getRoleByUsername(user.getName());
            user.setRole(role);
            
            String jwt = jwtUtil.getToken(user);
            return ResponseEntity.ok(jwt);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @RequestMapping("/decodetoken")
    public User decodeToken(@RequestParam(name = "token") String token) {
        return jwtUtil.getUser(token);
    }
}