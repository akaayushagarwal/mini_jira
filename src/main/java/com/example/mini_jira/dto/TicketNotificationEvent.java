package com.example.mini_jira.dto;

import com.example.mini_jira.entity.TicketEntity;

public record TicketNotificationEvent(

    Long ticketId,
    String ticketName,
    String status,
    String message,
    String email
) {
    public static TicketNotificationEvent fromEntity(TicketEntity entity, String email){
        return new TicketNotificationEvent(
            entity.getId(),
            entity.getTitle(),
            entity.getStatus(),
            "A new critical bug ticket has been created and requires immediate attention.",
            email
        );
    }
}
