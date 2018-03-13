package com.claro.moose.controllers;

import com.claro.moose.models.Role;
import com.claro.moose.models.User;
import com.claro.moose.util.authentication.JWTUtil;
import com.claro.moose.util.authentication.PAWClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private PAWClient pawClient;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam("user") String user, @RequestParam("token") long token) {

        //  String username = request.getParameter("user");
        //   long token = Long.parseLong(request.getParameter("token"));

        if (pawClient.isValidToken(user, token)) {
            User userObject = new User();
            userObject.setName(user);

            Role role = pawClient.getRoleByUsername(userObject.getName());
            userObject.setRole(role);

            String jwt = jwtUtil.getToken(userObject);
            return ResponseEntity.ok(jwt);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @RequestMapping("/api/decodetoken")
    public User decodeToken(@RequestParam String token) {
        return jwtUtil.getUser(token);
    }
}