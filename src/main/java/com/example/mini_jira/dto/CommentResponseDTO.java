package com.example.mini_jira.dto;

import com.example.mini_jira.entity.CommentEntity;

public record CommentResponseDTO(

    Long id,
    String text,
    String autherUserName,
    Long ticketId

) {

    public static CommentResponseDTO fromEntity(CommentEntity entity){

        return new CommentResponseDTO(
            entity.getId(),
            entity.getText(),
            entity.getAuthor().getUsername(),
            entity.getTicket().getId()
        );
    }

}
