package com.github.gabrielbb.controllers;

import com.github.gabrielbb.models.User;
import com.github.gabrielbb.repos.UserRepository;
import com.github.gabrielbb.util.JWTUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private JWTUtility jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(String name, String password) {

        final User user;
        if ((user = userRepo.findByNameAndPassword(name, password)) != null) {
            String jsonWebToken = jwtUtil.getToken(user);

            return ResponseEntity.status(HttpStatus.OK).body(jsonWebToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}