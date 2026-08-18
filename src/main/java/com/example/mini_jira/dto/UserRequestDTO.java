package com.example.mini_jira.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(

    @NotBlank(message = "Username can not be blank")
    String username,

    @NotBlank(message = "Password can not be blank")
    String password,

    @NotBlank(message = "Role can not be blank")
    String role,

    @Email(message = "Email Invalid")
    String email

) {

}
