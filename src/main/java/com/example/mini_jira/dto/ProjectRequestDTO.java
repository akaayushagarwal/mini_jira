package com.example.mini_jira.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDTO(

    @NotBlank(message = "Project name can not be blank")
    String name,

    String description

) {

}
