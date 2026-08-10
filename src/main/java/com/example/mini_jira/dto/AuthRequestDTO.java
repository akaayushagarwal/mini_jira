package com.example.mini_jira.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(

    @NotBlank(message = "Username can not be blank")
    String username,

    @NotBlank(message = "Password can't be blank")
    String password
) {}
