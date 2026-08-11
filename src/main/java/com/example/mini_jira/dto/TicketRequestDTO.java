package com.example.mini_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketRequestDTO(
    
    @NotBlank(message = "Title can not be blank")
    String title,

    @Size(max = 499, message = "Description size should be <500")
    String description,

    @NotNull(message = "Project Id can not be null")
    Long projectId

) {}
