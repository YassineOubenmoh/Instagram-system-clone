package com.yassine.insta_clone_backend.controllers;

import com.yassine.insta_clone_backend.dtos.AuthenticationRequest;
import com.yassine.insta_clone_backend.dtos.AuthenticationResponse;
import com.yassine.insta_clone_backend.dtos.RegisterDto;
import com.yassine.insta_clone_backend.services.AuthenticationService;
import com.yassine.insta_clone_backend.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDto registerDto){
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}