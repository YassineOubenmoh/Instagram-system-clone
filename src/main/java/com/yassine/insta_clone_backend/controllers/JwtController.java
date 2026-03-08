package com.yassine.insta_clone_backend.controllers;

import com.sun.net.httpserver.HttpsServer;
import com.yassine.insta_clone_backend.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/token")
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/username")
    public ResponseEntity<String> getUsernameFromToken(@RequestParam("token") String token){
        return new ResponseEntity<>(jwtService.extractUsername(token), HttpStatus.OK);
    }
}
