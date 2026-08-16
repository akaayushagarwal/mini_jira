package com.example.mini_jira.dto;

import java.io.Serializable;

import com.example.mini_jira.entity.TicketEntity;

public record TicketResponseDTO(

    Long id,
    String title,
    String description,
    String status,
    String reporterUserName,
    String assigneeUserName

) implements Serializable 
{

    public static TicketResponseDTO fromEntity(TicketEntity entity){
        return new TicketResponseDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getStatus(),
            entity.getReporter().getUsername(),
            entity.getAssignee() != null ? entity.getAssignee().getUsername(): "Unassigned"
        );
    }

}
