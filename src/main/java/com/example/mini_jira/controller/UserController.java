package com.example.mini_jira.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mini_jira.dto.UserRequestDTO;
import com.example.mini_jira.dto.UserResponseDTO;
import com.example.mini_jira.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDTO dto){

        String response = userService.registerUser(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){

        List<UserResponseDTO> allUsers = userService.fetchAllUsers();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(allUsers);
    }

}
