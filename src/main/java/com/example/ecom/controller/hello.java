package com.example.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class hello {
    @RequestMapping("/hello")
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("hello");
    }
}
