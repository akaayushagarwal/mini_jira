package com.example.mini_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequestDTO(

    @NotBlank(message = "Comment can not be blank")
    String text,

    @NotNull(message = "Ticket Id can not be null")
    Long ticketId
    
) {

}
