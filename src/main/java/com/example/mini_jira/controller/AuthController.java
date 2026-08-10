package com.example.mini_jira.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mini_jira.dto.AuthRequestDTO;
import com.example.mini_jira.service.JwtService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthRequestDTO dto) {
         
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );
        
        if(authentication.isAuthenticated()){
            
            String token = jwtService.generateToken(dto.username());

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("token", token));

        }

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .build();
    }
    

}
