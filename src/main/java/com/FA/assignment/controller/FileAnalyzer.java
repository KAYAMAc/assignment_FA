package com.FA.assignment.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class FileAnalyzer {

    @PostMapping("/")
    public String hello() {
        return "Hello, Spring Boot!";
    }


}
