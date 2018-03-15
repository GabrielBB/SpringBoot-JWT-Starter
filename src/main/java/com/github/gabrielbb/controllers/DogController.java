package com.github.gabrielbb.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dogs")
public class DogController {

    @RequestMapping("")
    public String index() {
        return "HAW HAW!";
    }
}