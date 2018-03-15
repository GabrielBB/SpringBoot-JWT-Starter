package com.github.gabrielbb.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cats")
public class CatController {

    @RequestMapping("")
    public String index() {
        return "MEOOOW";
    }
}