package com.purple.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public class AppSetupController {
    @GetMapping(path = "/csrf")
    public ResponseEntity<String> csrf() {
        return ResponseEntity.ok("token created");
    }
}
