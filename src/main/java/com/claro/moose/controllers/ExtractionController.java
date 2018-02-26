package com.claro.moose.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extraccion")
public class ExtractionController {

    @RequestMapping("")
    public String hello() {
        return "Hello";
    }
}